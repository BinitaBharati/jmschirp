(ns jmschirp.test.msg-browser
  (:import (bbharati.jmschirp.test JmsObjectFactory_Java))
  (:require [clojure.test :as ct]
            [clojure.pprint :as cpp]
            [jmschirp.msg-browser-helper :as mb]
            [jmschirp.util :as ju]))

(defn test-ns-hook []
;(ju/log-info "final OP = "(mb/inspect-object-begin(JmsObjectFactory_Java/generateObject 1)))
;(ju/log-info "OP is = "  (cpp/pprint (mb/filter-fields (CorObjGenerator/generateCR 1))))
(ju/log-info "after calling inspect-object-begin , final OP = " (ju/filter-fields (JmsObjectFactory_Java/generateObject 1) "bbharati.jmschirp.test"))
)


(ct/deftest test-inspect-object 
  (ct/testing "test-inspect-object"
           (cpp/pprint (ju/log-info "final OP = "(mb/inspect-object-begin(JmsObjectFactory_Java/generateObject 1))))
          ))