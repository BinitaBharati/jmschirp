(ns jmschirp.conn-details
  (:require ;[basil.public :as bp]
    [clostache.parser :as cp]
            [jmschirp.util :as ju]
             [jmschirp.adaptor :as jmsadp]))

(defn get-rest-conn-names [input]
  (->> "/.jmschirp/vendor-info.clj"
    (str (System/getProperty "user.home"))
    slurp
    ju/safe-read-string
    ju/echo
    (reduce (fn [result each]
              (let [input-conn-name (get input :name)]
                (if (not= input-conn-name (get each :name))
                  (conj result (get each :name))
                  result))) [])
    ju/echo))

(defn get-queue-list [input] 
  (let [provider-ns (-> (ju/get-conn-info input)
                      ju/echo
                      (ju/get-supported-vd-info)
                      ju/echo
                      (get :provider-ns)
                      ju/echo)]
    (try (->(jmsadp/resolve-provider provider-ns) 
           (jmsadp/get-queue-stat input)
           ju/add-id) 
      (catch Exception ex
        [{:exception-msg (.getMessage ex)}]))))

(defn render-conn-details [input]
    (let [conn-queue-list (get-queue-list input)]
    (ju/log-info "render-conn-details: conn-queue-list = "conn-queue-list)
    (if (contains? (first conn-queue-list) :exception-msg)  ;Exception occured
      (cp/render-resource "public/templates/connectiondetails-error.mustache" {:jmsConnectionName (get input :name)  
                                                                               :rest-conn-names (get-rest-conn-names input)
                                                                               :exception-msg (get-in (first conn-queue-list) [:exception-msg])})
      (cp/render-resource "public/templates/connectionDetails.mustache" {:jmsConnectionName (get input :name)  
                                                              :rest-conn-names (get-rest-conn-names input)
                                                              :conn-queue-list (get-queue-list input)}))))

(defn render-conn-tab1 [input]
  (cp/render-resource "public/templates/connectionDetailsTab1.mustache" {:jmsConnectionName (get input :name)  
                                              :conn-queue-list (get-queue-list input)}))