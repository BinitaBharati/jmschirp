(ns jmschirp.landing
  (:require [jmschirp.util :as ju]
            [jmschirp.adaptor :as jmsadp]
             [clojure.data.json :as json])
  (:import (java.io FileNotFoundException)))

(defn list-conn [session]
  (let [op (ju/list-conn session)]
    (if (empty? session)
      {:body (json/write-str op) :session {:data (atom {})}}
      {:body (json/write-str op) :session session})))


(defn get-valid-vd []
  (ju/get-valid-vd))

(defn test-conn [input]
  (ju/log-info "landing test-conn: entered with "input)
  (-> (ju/get-supported-vd-info input)
    ju/echo
    (get :provider-ns)
    ju/echo
    (jmsadp/resolve-provider) 
    ju/echo
    (jmsadp/test-conn input))
)

(defn save-conn [input]
  (ju/log-info "save-conn: entered with "input)
  (let [action (get input :action)
        create-map (fn [input] {:name (get input :connectionName) 
                            :type (get input :vendorType) 
                            :version (get input :vendorVersion) 
                            :host (get input :host) 
                            :port (get input :port) 
                            :adminUser (get input :adminUser) 
                            :adminPasswd (get input :adminPasswd) 
                            :jndiName (get input :jndiName) 
                            :jndiUser (get input :jndiUser) 
                            :jndiPasswd (get input :jndiPassword)
                            :jmxPort (get input :jmxPort) 
                            :jmxUser (get input :jmxUser) 
                            :jmxPasswd (get input :jmxPassword)})]
    (->> (case  action
           "0" (->> (create-map input)  
                 (conj (list-conn)))
           "1" (let [input-name (get input :connectionName)]
                 (->  (fn [each] (if (= (get each :name) input-name)
                                   (create-map input)
                                   each)) 
                   (map (list-conn))
                   vec))
           "2" (let [delete-name (get input :name)]
                 (-> (fn [each] (not= delete-name (get each :name)))
                   (filter (list-conn))
                   vec))) 
      pr-str
      (spit (str (System/getProperty "user.home") "/.jmschirp/vendor-info.clj")))
    ;return any dumb string to the client. None of the actions actually require a output.
    ;But, compujure mandates you to return some string, else, it will return a HTTP failure  
    ;response code to the client.
    true))

(defn transform-map [filter-map] 
  {:connectionName (get filter-map :name)
      :vendorType (get filter-map :type)
      :vendorVersion (get filter-map :version)
      :host (get filter-map :host)
      :port (get filter-map :port)
      :adminUser (get filter-map :adminUser)
      :adminPasswd (get filter-map :adminPasswd)
      :jndiName (get filter-map :jndiName)
      :jndiUser (get filter-map :jndiUser)
      :jndiPasswd (get filter-map :jndiPasswd)
      :jmxPort (get filter-map :jmxPort)
      :jmxUser (get filter-map :jmxUser)
      :jmxPassword (get filter-map :jmxPasswd)})

(defn get-conn-info2 [input]
  (-> (ju/get-conn-info input)
    (transform-map)))


