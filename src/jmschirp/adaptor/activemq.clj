(ns jmschirp.adaptor.activemq  
  (:require [jmschirp.adaptor :as adp]
            [jmschirp.util :as ju])
  (:import (java.util HashMap)
           (javax.jms Session) 
           (javax.management.remote JMXConnector JMXConnectorFactory JMXServiceURL)
           (javax.management ObjectName MBeanServerInvocationHandler)          
           (org.apache.activemq ActiveMQConnectionFactory)
           (org.apache.activemq.broker.jmx BrokerViewMBean)
           (jmschirp.adaptor Provider)))

  (defn get-conn-factory-impl [input]
    (ju/log-info "get-conn-factory: entered with " input)
    (new ActiveMQConnectionFactory (str "tcp://" (:host input) ":" (:port input)))
  )
 
  
(defn get-queue-stat2 [queue-info-array]
  (-> (fn [result each-queue-info] 
        (ju/log-info "get-queue-stat2: entered reducer fn; result type = " (type result) ", result = "result)
        (let [queue-name (.getName each-queue-info)]
          (if(not(.startsWith queue-name "$"))
                (do (->> 
                     (conj result {:name (.getName each-queue-info) 
                                  :pendingMessageCount (str (.getPendingMessageCount each-queue-info) "")
                                  :pendingMessageSize (str (.getPendingMessageSize each-queue-info) "")})
                     (ju/echo)
                     ))
                result)))
       (reduce [] queue-info-array)))


(defn get-jmx-connector [input]
  (let [
            jmx-port (:jmxPort input)
            jmx-usr (:jmxUser input)
            jmx-passwd (:jmxPasswd input)]
    (let [env {JMXConnector/CREDENTIALS (into-array [jmx-usr jmx-passwd])}
                connector (if (not (nil? jmx-usr)) 
                            (-> (format "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi" (get input :host) (get input :jmxPort))
                              (JMXServiceURL. )
                              (JMXConnectorFactory/connect env))
                            (-> (format "service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi" (get input :host) (get input :jmxPort))
                              (JMXServiceURL.)
                              (JMXConnectorFactory/connect)))]
       connector  ;return connector
)))

(defn test-conn-1 [input]
  (get-conn-factory-impl input))


(defn test-conn-2 [input]
  (-> (get-jmx-connector input)
    (.connect)))

(defn make-provider []
  (ju/log-info "make-provider entered")
  (reify Provider
    (-get-conn-factory [this input] 
      (get-conn-factory-impl (ju/get-conn-info input)))
    (-get-queue [this input] 
      (let [conn-info (ju/get-conn-info input)
            conn (-> (get-conn-factory-impl input)
                   (.createConnection (:adminUser conn-info) (:adminPasswd conn-info)))]
        (.start conn)
        (let [session (.createSession conn false Session/AUTO_ACKNOWLEDGE)
              queue-ref (.createQueue session (:queueName input))]
          (.close conn)
          (.close session)
          queue-ref)))
    (-get-queue-stat [this input]
      (let [conn-info (ju/get-conn-info input)
            connector (get-jmx-connector conn-info)]
        (.connect connector)
         (let [connection (.getMBeanServerConnection connector)
               mbeanName (new ObjectName (format "org.apache.activemq:type=Broker,brokerName=%s" (:host conn-info)))
               mbean (MBeanServerInvocationHandler/newProxyInstance connection mbeanName BrokerViewMBean true)
               qObjAry (.getQueues mbean)]
           (ju/log-info "get-queue-stat: connection = "connection ",mbean = "mbeanName ", qObjAry = "qObjAry)
           (-> (fn [result each]
                 (let [obj-canonical-name (.getCanonicalName each)
                       index (.indexOf obj-canonical-name "destinationName=") 
                       queueName 
                       (.substring obj-canonical-name 
                         (+ index (.length "destinationName="))
                         (.indexOf obj-canonical-name "," index))
                       queueSize (.getAttribute connection each "QueueSize")
                       MemoryUsageByteCount (.getAttribute connection each "MemoryUsageByteCount")]
                   (ju/log-info "get-queue-stat: obj-can-name = "obj-canonical-name ",queueName = "queueName 
                                ", queueSize = "queueSize ", MemoryUsageByteCount = "MemoryUsageByteCount)
                   (conj result {:name queueName 
                              :pendingMessageCount queueSize 
                              :pendingMessageSize MemoryUsageByteCount}))
                 ) (reduce [] qObjAry)))))
    (-test-conn [this input]
      (ju/log-info "amq-adapter - test-conn: entered with input = "input)
      (test-conn-1 input)
      (test-conn-2 input)
     )))