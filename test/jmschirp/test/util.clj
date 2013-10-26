(ns jmschirp.test.util
  (:require [clojure.test :as ct]
            [jmschirp.util :as ju]))

(ct/deftest test-top-pkg-structure
  (ct/testing
    (ju/log-info "after calling top-pkg-structure, op = " (ju/top-pkg-structure "df.as.df.GHOST"))))

(ct/deftest test-klass-valid-for-inspection
  (ct/testing
    (ju/log-info "after calling klass-valid-for-inspection , op = " (ju/klass-valid-for-inspection? "com.cisco" "services.assurance.ng.common.correlation.CorrelationEvent"))))

