(ns jmschirp.handler
  (:use compojure.core)
  (:require [clojure.java.io :as io]          
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.response :as cr]
            [clojure.data.json :as json]
            [ring.util.response :as resp]
            [jmschirp.landing :as home]
            [jmschirp.conn-details :as cd]
            [jmschirp.queue-details :as qd]
            [jmschirp.msg-browser :as mb]
             [jmschirp.util :as ju]))




(defroutes app-routes
  (GET "/jmschirp" [] (io/resource "public/index.html"))
  (GET "/list-connections" [] (json/write-str (home/list-conn)))
  (GET "/get-vendor-details" [] (json/write-str (home/get-valid-vd)))
  (POST "/save-connections" {params :params} (json/write-str(home/save-conn params)))
  (GET "/delete-connection" {params :params} (json/write-str(home/save-conn (assoc params :action "2"))))
  (GET "/get-connection-info" {params :params} (json/write-str (home/get-conn-info2 params)))
  (GET "/connection-details"  {params :params} (cd/render-conn-details params))
  (GET "/conn-tab1"  {params :params} (cd/render-conn-tab1 params))
  (GET "/queue-details"  {params :params session :session} (qd/render-queue-details params session))
  (GET "/queue-browser"  {params :params session :session}(ju/echo(qd/browse-queue params session)))
  (GET "/msg-browser"  {params :params session :session}(json/write-str(mb/inspect-msg params session)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))