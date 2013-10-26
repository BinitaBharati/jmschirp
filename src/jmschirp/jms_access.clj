(ns jmschirp.jms-access
  (:import (javax.naming Context InitialContext NamingException) (java.util Hashtable))
  (:require [jmschirp.landing :as home]
            [jmschirp.util :as util]))

(defn get-ems-jndi-context [input]
  (->> {Context/INITIAL_CONTEXT_FACTORY (doto "com.tibco.tibjms.naming.TibjmsInitialContextFactory" (Class/forName)),
                 Context/PROVIDER_URL (str "tibjmsnaming://" (get input :host) ":" (get input :port)),
                 Context/SECURITY_PRINCIPAL (get input :jndiUser),
                 Context/SECURITY_CREDENTIALS (get input :jndiPasswd)}
            (new Hashtable)
            (util/echo)
            (new InitialContext)
            (util/echo)))

(defn get-connection-factory [input]
  (let [vendor-type (get input :vendorType)]
    (case vendor-type
    "EMS" (-> (get-ems-jndi-context input)
            (.lookup (get input :jndiName))
            (util/echo)))))


(defn get-obj-msg-details [input]
  (->> (home/get-conn-info2 input)
    (util/echo)
    (get-connection-factory)))



