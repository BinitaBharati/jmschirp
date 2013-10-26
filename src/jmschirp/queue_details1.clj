(ns jmschirp.queue-details1
  (:import (javax.jms Session ObjectMessage TextMessage BytesMessage StreamMessage MapMessage))
  (:require [clostache.parser :as cp]
            [jmschirp.util :as ju]
            [jmschirp.adaptor :as jmsadp]
            [clojure.data.json :as json]))

(defn render-queue-details [params session]
  (ju/log-info "render-queue-details: entered with params = " params " session = " session)
   (cp/render-resource "public/templates/queueDetails.mustache" {:jmsConnectionName (get params :connection) 
                                                                 :queueName (get params :queue)}))

(defn get-queue-data-seq [params session]
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
     (get-in session [(:connection params) (:queue params) :pending-queue-ds])))

(defn update-queue-browser-session [queue-data-seq  params session]
(cond 
  (nil? (get-in session [:connection params])) ;connection itself absent
  (assoc session (:connection params) {(:queue params) {:pending-queue-ds queue-data-seq}})
  (nil? (get-in session [(:connection params) (:queue params)]))  ;connection presnt but queue absent
  (assoc session (:connection params) 
         (-> (get-in session [:connection params])
           (assoc (:queue params) {:pending-queue-ds queue-data-seq})))
  :else  ;connection and queue entry present
  (->>(->(get-in session [(:connection params) (:queue params)])
        (assoc :pending-queue-ds queue-data-seq))
    (assoc (get-in session [(:connection)]) (:queue params))
    (assoc session (:connection params)))))

(defn save-qds-session [queue-data-seq  params session]
  (assoc session (:connection params) {(:queue params) {:orig-queue-ds queue-data-seq}})
)

(def serving-size 50)  ;; TODO make this configurable


(defn browse-queue-response
  [params session queue-data-seq hasMore queue-data-vector]
  (ju/log-info "browse-queue-response: entered with queue-data-seq "queue-data-seq = ",queue-data-vector = "queue-data-vector)
  (if (empty? queue-data-seq)
    {:body (json/write-str{:hasMore hasMore 
          :msgData queue-data-vector :empty true})
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


(defn browse-queue
  "Given `params` map (containing connection name, queue name), HTTP `session` fetch `n` messages from the JMS queue
  and return {:body [msg1 msg2..] :session {conn-name {queue-name queue-data-vec}}}"
  [params session]
  (ju/log-info "browse-queue: entered with params " params " session "session)
  (let [queue-data-seq (get-queue-data-seq params session)]
    (ju/echo (str "browse-queue: queue-data-seq1 "queue-data-seq))
    (save-qds-session queue-data-seq params session)
    (if (empty? queue-data-seq)
      (browse-queue-response params session queue-data-seq "N" ["Queue is empty! No data to show."])
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



