(ns jmschirp.test.stm-sample
   (:require 
            [jmschirp.util :as ju]))

(defn get-ref []
  (ju/log-info "get-ref: entered")
  (ref {}))

(def session-ref (get-ref))

(defn update-session-ref [input]
  (ju/log-info "update-session-ref: entered with  "input "and session-ref = "@session-ref)
  (dosync ; STM trxn
    (cond
     (nil? (get-in @session-ref [(:connection input)])) ;connection itself absent
     (do   
       (ju/log-info "cond1 - session-ref = "@session-ref)
       (alter session-ref assoc (:connection input) {(:queue input) {:pending-queue-ds (:pending-queue-ds input)}})
       (ju/log-info "cond1 later - session-ref = "@session-ref))
     (nil? (get-in @session-ref [(get input :connection) (get input :queue)]))  ;connection present but queue absent
     (do 
        (ju/log-info "cond2 - session-ref = "@session-ref)
       (alter session-ref assoc (:connection input) 
                                (-> (get-in @session-ref [(:connection input)])
                                  (ju/echo-wit-msg "1111 - echo")
                                  (assoc (:queue input) {:pending-queue-ds (:pending-queue-ds input)})
                                  (ju/echo-wit-msg "222 - echo")))
       (ju/log-info "cond2 later - session-ref = "@session-ref))
     :else  ;connection and queue entry present
    (do
      (ju/log-info "cond3 - session-ref = "@session-ref)
      (alter session-ref assoc-in [(:connection input) (:queue input) :pending-queue-ds] (:pending-queue-ds input))
      (ju/log-info "cond3 later - session-ref = "@session-ref)))))

(defn handle-thread-sleep [sleep-in-millis]
  (try  
    (Thread/sleep sleep-in-millis) 
    (catch Exception ex
      (ju/log-info "Exception occured - " (.getMessage ex)))))

(def future1 (future 
               (handle-thread-sleep 1000)
               (update-session-ref {:connection "conn1" :queue "q1" :pending-queue-ds "123"})
               @session-ref))

(def future2 (future 
               (handle-thread-sleep 1000)
               (update-session-ref {:connection "conn1" :queue "q2" :pending-queue-ds "456"})
               @session-ref))

(def future3 (future 
               (handle-thread-sleep 2000)
               (update-session-ref {:connection "conn1" :queue "q1" :pending-queue-ds "123-456"})
               @session-ref))

(def future4 (future 
               (handle-thread-sleep 2000)
               (update-session-ref {:connection "conn1" :queue "q2" :pending-queue-ds "456-789"})
               @session-ref))

(ju/log-info "future1 : op = "@future1)
(ju/log-info "future2 : op = "@future2)
(ju/log-info "future3 : op = "@future3)
(ju/log-info "future4 : op = "@future4)
(ju/log-info "finally session-ref = "@session-ref)