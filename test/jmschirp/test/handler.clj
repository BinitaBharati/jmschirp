(ns jmschirp.test.handler
  (:use clojure.test
        ring.mock.request  
        jmschirp.handler)
  (:require [jmschirp.jms-access :as jmsaccess]
            [jmschirp.conn-details :as cd]))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello World"))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))



(deftest test-jms-conn
  (testing "Tibco"
    (jmsaccess/get-connection-factory {:host "10.76.85.211" :port "7222" :jndiUser "admin" :jndiPasswd "" :vendorType "EMS" :jndiName "FTQueueConnectionFactory"}))
  (testing "ActiveMQ"))
;;(println "huhu" (get-obj-msg-details {:name "ems1", :queue "emsObjQ1", :msg-id "ID:EMS-SERVER.64AB51C48F0A9E8:10"}))






