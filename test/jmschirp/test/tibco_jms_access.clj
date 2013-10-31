(ns jmschirp.test.tibco_jms_access
  (:use clojure.test)
  (:require [jmschirp.adaptor :as jmsadp]))

(deftest get-queue-stat-test
  (testing "get-queue-stat"
           (jmsadp/get-queue-stat (jmsadp/resolve-provider "jmschirp.adaptor.tibco") {:name "ems1"})))

