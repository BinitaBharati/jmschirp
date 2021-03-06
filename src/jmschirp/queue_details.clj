(ns jmschirp.queue-details
  (:import (javax.jms Session ObjectMessage TextMessage BytesMessage StreamMessage MapMessage))
  (:require [clostache.parser :as cp]
            [jmschirp.util :as ju]
            [jmschirp.adaptor :as jmsadp]
            [clojure.data.json :as json]
            [clojure.string :as cs]))

(defn encode-queue-name [queue-name]
  (cond
    (not (nil? (re-seq #"\." queue-name)))  ;queue-name contains dot
    (cs/replace queue-name #"\." "-")
    (not (nil? (re-seq #"-" queue-name)))
    (cs/replace queue-name #"-+" #(str %1 "-"))
    :else queue-name
    ))

(defn render-queue-details [params session]
  (ju/log-info "render-queue-details: entered with params = " params " session = " session)
   (cp/render-resource "public/templates/queueDetails.mustache" {:jmsConnectionName (get params :connection) 
                                                                 :queueName (get params :queue)
                                                                 :encodedQName (encode-queue-name (get params :queue))}))
(defn get-queue-data-seq [params session]
  (ju/log-info "get-queue-data-seq: entered with "params)
   (if (= (get params :isScrolled) "N");fresh request
     (let [conn-info (ju/get-conn-info {:name (get params :connection)})
           provider-ns (-> conn-info
                         ju/echo
                         (ju/get-supported-vd-info)
                         ju/echo
                         (get :provider-ns)
                         ju/echo)]
       (ju/log-info "provider-ns = " provider-ns)
       (let [conn-factory (jmsadp/get-conn-factory (jmsadp/resolve-provider provider-ns) conn-info)
             queue-ref (jmsadp/get-queue (jmsadp/resolve-provider provider-ns) (assoc conn-info :queueName (get params :queue)))
             conn (.createConnection  conn-factory)]
         (.start conn)
         (-> conn
           (.createSession false Session/AUTO_ACKNOWLEDGE)
           (.createBrowser queue-ref)
           (.getEnumeration)
           (enumeration-seq))))
     (get-in @(:data session) [(:connection params) (:queue params) :pending-queue-ds])))


(defn update-queue-browser-session [queue-data-seq  params session]
  (ju/log-info "update-queue-browser-session: entered with params = " params ", session = "session ", queue-data-seq = "queue-data-seq)
  (ju/log-info "update-queue-browser-session: 111 "(get-in session [:data]))
  (let [session-atom (get-in session [:data])]
    (ju/log-info "update-queue-browser-session: session-atom = "session-atom)
    (cond 
     (nil? (get-in @session-atom [(:connection params)])) ;connection itself absent
       (do   
         (ju/log-info "cond1 - session-atom = "session-atom)
         (swap! session-atom assoc (:connection params) {(:queue params) {:pending-queue-ds queue-data-seq}})
         session)
       (nil? (get-in @session-atom [(get params :connection) (get params :queue)]))  ;connection present but queue absent
       (do 
         (ju/log-info "cond2 - session-atom = "@session-atom)
            (swap! session-atom
                   (fn [session-data]
                     (assoc session-data (:connection params) 
                            (-> (get-in session-data [(:connection params)])
                              (ju/echo-wit-msg "1111 - echo")
                              (assoc (:queue params) {:pending-queue-ds queue-data-seq})
                              (ju/echo-wit-msg "222 - echo")))))
            session)
     :else  ;connection and queue entry present
       (do
         (ju/log-info "cond3 - session-atom = "@session-atom)
          (swap! session-atom assoc-in [(:connection params) (:queue params) :pending-queue-ds] (:pending-queue-ds queue-data-seq))
          session))))



(defn save-qds-session [queue-data-seq  params session]
  (assoc session (:connection params) {(:queue params) {:orig-queue-ds queue-data-seq}})
)

(def serving-size 50)  ;; TODO make this configurable


(defn browse-queue-response
  [params session queue-data-seq hasMore queue-data-vector]
  (ju/log-info "browse-queue-response: entered with queue-data-seq "queue-data-seq = ",queue-data-vector = "queue-data-vector ", session = "session)
  (if (empty? queue-data-vector)
    {:body (json/write-str{:hasMore hasMore 
                           :msgData ["Queue is empty! No data to show."]
                           :empty true})
     :session (update-queue-browser-session []  params session)
   }
    {:body (json/write-str{:hasMore hasMore 
                           :msgData queue-data-vector})
     :session (update-queue-browser-session queue-data-seq  params session)
   }))

(defn populate-jms-msg-type [msg]
  (cond 
    (instance? ObjectMessage msg)
    "ObjectMessage"
     (instance? TextMessage msg)
    "TextMessage"
     (instance? BytesMessage msg)
    "BytesMessage"
     (instance? StreamMessage msg)
    "StreamMessage"
    (instance? MapMessage msg)
    "MapMessage"))


(defn browse-queue2
  "Given `params` map (containing connection name, queue name), HTTP `session` fetch `n` messages from the JMS queue
  and return {:body [msg1 msg2..] :session {conn-name {queue-name queue-data-vec}}}"
  [params session]
  (ju/log-info "browse-queue2: entered with params " params " session "session)
  (let [queue-data-seq (get-queue-data-seq params session)]
    (ju/log-info (str "browse-queue2: queue-data-seq = "queue-data-seq))
    ;(save-qds-session queue-data-seq params session)
    ;(ju/log-info "browse-queue2: after calling save-qds-session, session = "session)
    (if (empty? queue-data-seq)
      (browse-queue-response params session queue-data-seq "N" []) ;empty queue
       (->> queue-data-seq
      ju/echo
      (map vector (iterate inc 1))
      ju/echo
      (reduce (fn [result [row-count each]]
                (ju/log-info "test1: row-count "row-count " each "each)
                (cond
                  (and (empty? queue-data-seq)
                       (< row-count serving-size))
                  (do
                    (ju/log-info "browse-queue: cond1")
                    (reduced (browse-queue-response params session (drop (count result) queue-data-seq) "N" result)))
                  (and (seq queue-data-seq);seq -> not empty short form
                       (= row-count serving-size))
                  (do 
                    (ju/log-info "browse-queue: in cond2") 
                    (reduced (browse-queue-response params session (drop (count result) queue-data-seq) "Y" result)))
                  (= (count queue-data-seq) 1)
                  (do 
                    (ju/log-info "browse-queue: in cond3") 
                    (reduced(->>  (conj result {"JMSMessageID" (.getJMSMessageID each)
                             "JMSRedelivered" (.getJMSRedelivered each)
                             "JMSCorrelationID" (.getJMSCorrelationID each)
                             "type" (populate-jms-msg-type each)})
                                          (browse-queue-response params session (drop (count result) queue-data-seq) "N"))))
                  :else  
                  (do
                    (ju/log-info "browse-queue: in else part")
                    (conj result {"JMSMessageID" (.getJMSMessageID each)
                                 "JMSRedelivered" (.getJMSRedelivered each)
                                 "JMSCorrelationID" (.getJMSCorrelationID each)
                                 "type" (populate-jms-msg-type each)}))))
              [])))))

(defn browse-queue 
  "Given `params` map (containing connection name, queue name), HTTP `session` fetch `n` messages from the JMS queue
  and return {:body [msg1 msg2..] :session {conn-name {queue-name queue-data-vec}}}"
  [params session]
  (ju/log-info "browse-queue: entered with params = "params ", session = "session)
  (let [browse-q-op (browse-queue2 params session)]
    (if (nil? (get-in browse-q-op [:body]))  ;Implies browse-queue-response wasnt called.The call to browse-queue2 exited from the else condition
      (browse-queue-response params session nil "N" browse-q-op)
      browse-q-op  ;else return with browse-q-op                                       
                                             
      ))
  )



