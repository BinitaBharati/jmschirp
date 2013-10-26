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
      (ju/add-id (jmsadp/get-queue-stat (jmsadp/resolve-provider provider-ns) input))))

(defn render-conn-details [input]
  (cp/render-resource "public/templates/connectionDetails.mustache" {:jmsConnectionName (get input :name)  
                                                              :rest-conn-names (get-rest-conn-names input)
                                                              :conn-queue-list (get-queue-list input)}))

(defn render-conn-tab1 [input]
  (cp/render-resource "public/templates/connectionDetailsTab1.mustache" {:jmsConnectionName (get input :name)  
                                                        :conn-queue-list (get-queue-list input)}))