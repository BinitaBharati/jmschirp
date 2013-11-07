(ns jmschirp.msg-browser
  (:import (clojure.lang Reflector) (java.util Collection) (javax.jms Session ObjectMessage TextMessage BytesMessage StreamMessage MapMessage))
  (:require [jmschirp.util :as ju]
            [jmschirp.adaptor :as jmsadp]
            [jmschirp.msg-browser-helper :as mbh]))

(defn get-queue-data-seq [params session]
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
           (enumeration-seq)))))

(defn get-msg-in-q [params session]
   (let [qds (get-queue-data-seq params session)]
    (-> (fn [each-msg]
          (= (.getJMSMessageID each-msg) (:jmsMsgId params)))
      (filter qds))))

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

(defn inspect-msg [params session]
  (ju/log-info "inspect-msg: entered with params = "params ", session = "session)
  (let [filter-msg (get-msg-in-q params session)
        msg-type (populate-jms-msg-type (nth filter-msg 0))]
    (ju/log-info "inspect-msg: filter-msg = "filter-msg)
    (if (not (empty? filter-msg))
      (cond
        (= msg-type "ObjectMessage")
        (try {:responseType "tree" :value (mbh/inspect-object-begin (.getObject (nth filter-msg 0)))}
          (catch Exception ex  ;getObject may throw DeserializationException
            (ju/log-info "inspect-msg: Exception occured : "ex)
            {:responseType "exception" :value (.getMessage ex)}
            ))
        ;{:responseType "tree" :value (mbh/inspect-object-begin (.getObject (nth filter-msg 0)))}
        (= msg-type "TextMessage")
        {:responseType "plain" :value (.getText (nth filter-msg 0))}
        (or (= msg-type "StreamMessage") (= msg-type "MapMessage"))
        (do
          (let [filter-msg-str (.toString (nth filter-msg 0))]
            {:responseType "plain" :value (.substring filter-msg-str (+ (.indexOf filter-msg-str "Fields=") (.length "Fields=")) (.lastIndexOf filter-msg-str "}"))})))
      {:responseType "plain" :value "MSG not found in queue!"})))



