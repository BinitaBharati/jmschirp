(ns jmschirp.test.amq-jms-access
   (:use clojure.test)
   (:require [jmschirp.adaptor :as jmsadp]
             [jmschirp.util :as ju]))

(deftest test-amq-queue-stat
  (testing "test-amq-queue-stat"
  (ju/log-info "final op is = " (jmsadp/get-queue-stat (jmsadp/resolve-provider "jmschirp.adaptor.activemq") {:name "activemq_localhost"}))))



