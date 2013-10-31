(ns jmschirp.test.jms-tibco-obj-msg-sender
  (:import (bbharati.jmschirp.test1 TstClassObjGenerator)
           (bbharati.jmschirp.test JmsObjectFactory_Java)
           (javax.jms Session ObjectMessage TextMessage BytesMessage StreamMessage MapMessage MessageProducer))
  (:require 
    [clojure.test :as ct]
    [jmschirp.adaptor :as jmsadp]
     [jmschirp.util :as ju]))

(defn get-jms-access-params [params]
 (let [conn-info (ju/get-conn-info {:name (get params :connection)})
           provider-ns (-> conn-info
                         ju/echo
                         (ju/get-supported-vd-info)
                         ju/echo
                         (get :provider-ns)
                         ju/echo)
           result {}]
       (ju/log-info "provider-ns = " provider-ns)
       (let [conn-factory (jmsadp/get-conn-factory (jmsadp/resolve-provider provider-ns) conn-info)
             queue-ref (jmsadp/get-queue (jmsadp/resolve-provider provider-ns) (assoc conn-info :queueName (get params :queue)))
             conn (.createConnection  conn-factory)
             session (.createSession conn false Session/AUTO_ACKNOWLEDGE)
             producer (.createProducer session queue-ref)]
         ;(.start conn)
         (ju/log-info session  "****" producer "****")
         {:session session :producer producer}
     )))

(ct/deftest send-obj-msg []
  (ct/testing "send-obj-msg"              
        (let [jms-access-params (get-jms-access-params {:connection "tibco_70" :queue "tst4"})]
          (loop [count 1000
                index 1]          
           (if (= index count)
           {}
           (do 
             (let [msg (TstClassObjGenerator/generateObj index)]
               (ju/log-info "send-obj-msg: jms-access-params = "jms-access-params ", msg = "msg)
               (->> (.createObjectMessage (get-in jms-access-params [:session]) msg)
                 (.send (get-in jms-access-params [:producer])))
               )
              (recur count (inc index))))))))

