(ns jmschirp.test.atom-sample
   (:require 
            [jmschirp.util :as ju]))

(defn get-atom []
  (ju/log-info "get-atom: entered")
  (atom {}))

(def session-atom (get-atom))

(defn update-session-atom [input]
  (ju/log-info "update-session-atom: entered with  "input "and session-atom = "@session-atom)
   (cond
     (nil? (get-in @session-atom [(:connection input)])) ;connection itself absent
     (do   
       (ju/log-info "cond1 - session-atom = "@session-atom)
       (swap! session-atom assoc (:connection input) {(:queue input) {:pending-queue-ds (:pending-queue-ds input)}})
       (ju/log-info "cond1 later - session-atom = "@session-atom))
     (nil? (get-in @session-atom [(get input :connection) (get input :queue)]))  ;connection present but queue absent
     (do 
       (ju/log-info "cond2 - session-atom = "@session-atom)
       (swap! session-atom assoc (:connection input) 
                                (-> (get-in @session-atom [(:connection input)])
                                  (ju/echo-wit-msg "1111 - echo")
                                  (assoc (:queue input) {:pending-queue-ds (:pending-queue-ds input)})
                                  (ju/echo-wit-msg "222 - echo")))
       (ju/log-info "cond2 later - session-atom = "@session-atom))
     :else  ;connection and queue entry present
     (do
       (ju/log-info "cond3 - session-atom = "@session-atom)
       (swap! session-atom assoc-in [(:connection input) (:queue input) :pending-queue-ds] (:pending-queue-ds input))
       (ju/log-info "cond3 later - session-atom = "@session-atom)))
  )

(defn handle-thread-sleep [sleep-in-millis]
  (try  
    (Thread/sleep sleep-in-millis) 
    (catch Exception ex
      (ju/log-info "Exception occured - " (.getMessage ex)))))

(def future1 (future 
               (handle-thread-sleep 1000)
               (update-session-atom {:connection "conn1" :queue "q1" :pending-queue-ds "123"})
               @session-atom))

(def future2 (future 
               (handle-thread-sleep 1000)
               (update-session-atom {:connection "conn1" :queue "q2" :pending-queue-ds "456"})
               @session-atom))

(def future3 (future 
               (handle-thread-sleep 2000)
               (update-session-atom {:connection "conn1" :queue "q1" :pending-queue-ds "123-456"})
               @session-atom))

(def future4 (future 
               (handle-thread-sleep 2000)
               (update-session-atom {:connection "conn1" :queue "q2" :pending-queue-ds "456-789"})
               @session-atom))

(ju/log-info "future1 : op = "@future1)
(ju/log-info "future2 : op = "@future2)
(ju/log-info "future3 : op = "@future3)
(ju/log-info "future4 : op = "@future4)
(ju/log-info "finally session-ref = "@session-atom)

