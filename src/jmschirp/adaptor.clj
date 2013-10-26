(ns jmschirp.adaptor)

(
  defprotocol Provider
  (-get-conn-factory [this input] "Implementations should return instance of javax.jms.ConnectionFactory")
  (-get-queue [this input] "Implementations should return instance of javax.jms.Queue")
  (-get-queue-stat [this input] "Implementations should return queue stats like PendingMessageCount, PendingMessageSize etc"))


(defn get-conn-factory [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-conn-factory provider-impl input))

(defn get-queue [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-queue provider-impl input))

(defn get-queue-stat [provider-impl input] {:pre [(satisfies? Provider provider-impl) (map? input)]}
  (-get-queue-stat provider-impl input))

(defn resolve-provider
  "Locate `make-provider` fn in namespace `the-ns`, returning a Provider instance."
  [the-ns] {:pre [(string? the-ns)]}
  (-> (doto (symbol the-ns) (require))
    (ns-resolve 'make-provider)
    deref
    (apply [])))