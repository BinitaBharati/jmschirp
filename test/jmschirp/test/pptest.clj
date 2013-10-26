(ns jmschirp.test.pptest
  (:require [clojure.pprint :as pp]
            [clojure.test :as ct]))

(ct/deftest pprint-op
 (ct/testing "ddd"
       (pp/pprint (read-string (slurp "/Users/bbharati/Desktop/2.txt")))))