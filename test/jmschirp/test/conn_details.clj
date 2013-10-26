(ns jmschirp.test.conn_details
  (:use clojure.test
        ring.mock.request  
        jmschirp.handler)
  (:require [jmschirp.conn-details :as cd]
            [jmschirp.adaptor :as jmsadp]
            [jmschirp.util :as ju]))

(deftest test-get-rest-conn-names
  (testing "get-rest-conn-names"
           (cd/get-rest-conn-names {:name "ems1"})))

(deftest test-get-queue-list
  (testing "get-queue-list"
  (ju/log-info "test-get-queue-list: output = " (cd/get-queue-list {:name "ems1"}))))

