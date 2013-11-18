(defproject jmschirp "0.1.1"
  :description "A web based JMS browser for everyone"
  :url "https://github.com/BinitaBharati/jmschirp"
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
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]
                                  [log4j/log4j "1.2.17"]
                                   ;Refer to Provider jars in Maven local repo. Use lein-localrepo to install jars in local Maven repo
                                  [com.tibco/tibems "7.0"]
                                  [com.tibco/tibjmsadmin "7.0"]
                                  [org.apache.activemq/activemq-broker "5.8.0"]
                                  [org.apache.activemq/activemq-all "5.8.0"]
                                  [org.apache.activemq/activemq-openwire-legacy "5.8.0"]
                                  [org.apache.activemq/activemq-client "5.8.0"]
                                  [org.apache.activemq/activemq-amqp "5.8.0"]]}
             :uberjar {:aot [jmschirp.main] ;Ahead of time compile jmschirp.main
                       :dependencies [[ring/ring-jetty-adapter "1.2.1"]]
                       :main jmschirp.main  ;Add the main class into the generated jar's MANIFEST
                       }}
  :java-source-paths ["java"])
