(ns jmschirp.main
  (:require [jmschirp.handler :as h]
            [ring.adapter.jetty :as j])
  (:gen-class)  ;Required to generate the class - jmschirp/main.class during AOT 
  )



(defn -main [& args]
  ;; (Class/forName "org.apache.activemq.ActiveMQConnectionFactory")
  (j/run-jetty (var h/app) {:port (if-let [port-str (first args)]
                                    (Integer/parseInt port-str)
                                    3000)}))

