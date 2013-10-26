(ns jmschirp.adaptor.tibco  
  (:require [jmschirp.adaptor :as adp]
            [jmschirp.util :as ju])
  (:import (jmschirp.adaptor Provider)
           (com.tibco.tibjms.admin TibjmsAdmin)
           (javax.naming Context InitialContext NamingException) 
           (java.util Hashtable)))

  (defn get-ems-jndi-context [input]
    (ju/log-info "get-ems-jndi-context: entered with " input)
  (->> {Context/INITIAL_CONTEXT_FACTORY (doto "com.tibco.tibjms.naming.TibjmsInitialContextFactory" (Class/forName)),
                 Context/PROVIDER_URL (str "tibjmsnaming://" (get input :host) ":" (get input :port)),
                 Context/SECURITY_PRINCIPAL (get input :jndiUser),
                 Context/SECURITY_CREDENTIALS (get input :jndiPasswd)}
            (new Hashtable)
            (ju/echo)
            (new InitialContext)
            (ju/echo)))
 
  
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

(defn make-provider []
  (ju/log-info "make-provider entered")
  (reify Provider
    (-get-conn-factory [this input] 
      (-> (get-ems-jndi-context input)
            (.lookup (get input :jndiName))
            (ju/echo)))
    (-get-queue [this input] 
      (-> (get-ems-jndi-context input)
        (.lookup (get input :queueName))))
    (-get-queue-stat [this input]
      (let [conn-info (ju/get-conn-info input)]
         (-> (format "tcp://%s:%s" (get conn-info :host) (get conn-info :port))
           ju/echo
         (TibjmsAdmin. (get conn-info :adminUser) (get conn-info :adminPasswd))
         (ju/echo)
          (.getQueues)
          (ju/echo)
          (get-queue-stat2)
          ju/echo)))))