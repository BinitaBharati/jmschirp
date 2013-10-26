(defproject jmschirp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [org.clojure/data.json "0.2.3"]
                 [ring-mock "0.1.5"]
                 [org.jboss.javaee/jboss-jms-api "1.1.0.GA"]
                ;[basil "0.4.1"]
                 [de.ubercode.clostache/clostache "1.3.1"]
                [com.taoensso/timbre "2.6.2"]
                [org.clojure/tools.logging "0.2.6"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler jmschirp.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]
                        [log4j/log4j "1.2.17"]
                         ;Refer to Tibco jars in Maven local repo. Use lein-localrepo to install jars in local Maven repo
                        [com.tibco/tibems "4.4.1"]
                        [com.tibco/tibjmsadmin "4.4.1"]
                        [cisco-jms-chirp "0.1.0-SNAPSHOT"]
                        ]}}
  :java-source-paths ["java"])
