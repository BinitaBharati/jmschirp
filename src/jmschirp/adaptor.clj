(ns jmschirp.adaptor)

(defprotocol Provider
  (-get-conn-factory [this input] "Implementations should return instance of javax.jms.ConnectionFactory")
  (-get-queue [this input] "Implementations should return instance of javax.jms.Queue")
  (-get-queue-stat [this input] "Implementations should return queue stats like PendingMessageCount, PendingMessageSize etc")
  (-test-conn [this input] "Implementations should test the validity of the connection details"))

(defn get-conn-factory [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-conn-factory provider-impl input))

(defn get-queue [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-queue provider-impl input))

(defn get-queue-stat [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-queue-stat provider-impl input))

(defn test-conn [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (Thread/sleep 60000)
  (try (-test-conn provider-impl input)
    {:isValid true :validityResult "Success!"}
    (catch Exception ex
     {:isValid false :validityResult "Failed!"})))

(defn resolve-provider
  "Locate `make-provider` fn in namespace `the-ns`, returning a Provider instance."
  [the-ns] {:pre [(string? the-ns)]}
  (-> (doto (symbol the-ns) (require))
    (ns-resolve 'make-provider)
    deref
    (apply [])))