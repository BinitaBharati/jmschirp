(ns jmschirp.msg-browser-helper
  (:import (clojure.lang Reflector) (java.util Collection))
  (:require [clojure.reflect :as cr]
            [clojure.pprint :as cpp]
            [jmschirp.util :as ju]))

(def str-or-num? (some-fn string? number?))

(def str-or-num-or-char? (some-fn string? number? char?))

(defn is-boolean? [input]
  (cond
    (or (instance? Boolean input) (instance? Boolean/TYPE input))
    true
    :else
    false))

(defn any-coll? [input]
 (instance? java.util.Collection input))

(defn any-map? [input]
(instance? java.util.Map input))

(defn any-array? [klass] {:pre [(instance? Class klass)]}
  (.isArray klass)
  )

(defn primitive-array? [klass] {:pre [(any-array? klass)]}
 (.isPrimitive (.getComponentType klass))
 )


(defn filter-fields [obj]
  (->> obj
    (cr/reflect)
    (ju/echo);{:bases #{java.io.Serializable java.lang.Object}, :flags #{:public}, :members #{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}}
    (:members)
     (ju/echo);#{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}
     (filter 
       (fn [each] (instance? clojure.reflect.Field each)))
    (ju/echo);(#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}})
    (filter 
      (fn [each] (let [flags-set (:flags each)]
                    (not (or (every? flags-set [:private :static :final]) ;(and (contains? flags-set :private) (contains? flags-set :static) (contains? flags-set :final))
                             (every? flags-set [:public :static :final]))))))
    (ju/echo)
    (into [])
    (ju/echo)))


(declare inspect-any)
(declare inspect-map)
(declare inspect-coll)
(declare inspect-object)
(declare inspect-oob-array)

(defn resolve-container-title [each-field input]
  (ju/log-info "resolve-container-title: entered for field-name = "(:name each-field) ",input = "input)
  (let  [field-name (:name each-field)
         container-class (type input)]
    (loop [result [] 
           temp input]  
     (ju/log-info "resolve-container-title: result = "result ",temp = "temp)
     (cond 
     (any-map? temp)
     (do (let [[key,value] (first temp)
               last-res (last result)]
          (ju/log-info "resolve-container-title: input is a map, first key and value = "key ", "value ", result = "result)
          (cond 
            (vector? last-res)
            (recur (conj
                     (->> (take (dec (count result)) result)
                       (ju/echo)
                       (into [])
                       (ju/echo)) 
                     (-> last-res
                       (ju/echo)
                       (conj (type temp))
                       (ju/echo)
                       (conj [(str (.getName (.getClass key)) ",")])
                       (ju/echo))) value)                      
            :else
            (recur (-> (conj result (type temp))
                    (ju/echo)
                    (conj [(str (.getName (.getClass key)) ",")])
                    (ju/echo)) value))))
     (any-coll? temp)
     (do
       (let [first-item (first temp)
             last-res (last result)
             first-item-klass (class first-item)]
         (ju/log-info "set-container-info: input is a coll, first-item = "first-item ", result = "result)
         (cond
           (vector? last-res)
       (recur (conj
                (->> (take (dec (count result)) result)
                   (ju/echo)
                   (into [])
                    (ju/echo)) 
                (-> last-res
                  (ju/echo)
                  (conj (type temp))
                (ju/echo))) first-item)

                :else
                (recur (conj result (type temp)) first-item))))
     :else  ;str or num or customized object
     (do
       (ju/log-info "resolve-container-title: input is a str-or-num, temp = "temp " result = "result)
       (let [last-res (last result)]         
       (cond
         (vector? last-res)
         (str field-name  (conj (->> (take (dec (count result)) result)
                          (into [])) 
                         (conj last-res (type temp))))
         :else
         (str field-name (conj result [(type temp)])))
         ))))))

(defn inspect-any-field [each-field input] 
  (ju/log-info "inspect-any-field: entered with input = "input)
  (when (not (nil? input))
    (cond
      (any-map? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-map input)}) 
      (any-coll? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-coll input)})
      (any-array? (class input))
      (do (if (str-or-num-or-char? (nth input 0))  ;oob array
            (do (ju/log-info "inspect-any-field: str-or-num-or-char array found !")
              {:isFolder true :title (str (:name each-field) (type input)) :children  (inspect-oob-array input)})       
            (do (ju/log-info "inspect-any-field: str-or-num-or-char array not found")
              {:isFolder true :title (str (:name each-field) (type input)) :children  (inspect-coll (into [] input))})))
      (or (str-or-num-or-char? input) (is-boolean? input))
	    ;{:isFolder false :title (str (:name each-field) "(" (type input) ") = " input)}
     {:isFolder false :title (str (:name each-field) "(" (:type each-field) ") = " input)}     
;     (is-boolean? input)
;     (do
;       {:isFolder false :title (str (:name each-field) " = " input)})
     (.startsWith (.getName (.getClass input)) "java")  ;OOB input found!
      (do 
        (ju/log-info "inspect-any-field: OOB java field found!")
        {:isFolder false :title (str (:name each-field) "(" (:type each-field) ") = " input)})
     :else  ;Object
     {:isFolder true :title (resolve-container-title each-field input) :children (inspect-object input)})))

(defn inspect-any [input]
  (ju/log-info "inspect-any: entered with input = "input)
  (cond
    (any-map? input)
    (inspect-map input)
    (any-coll? input)
    (inspect-coll input)
    ;    (str-or-num-or-char? input)
    (or (str-or-num-or-char? input) (is-boolean? input))
    [input] 
    (.startsWith (.getName (.getClass input)) "java")  ;OOB input found!
    [input]
    :else  ;Object
    (inspect-object input)))

(defn inspect-map [input] {:pre [(any-map? input)]}
   (ju/log-info "inspect-map: entered with "input)
   (do
      (-> (fn [result [map-key map-val]]  
            (ju/log-info "inspect-map: map-key = "map-key ",map-val = "map-val ",resullt = "result)
            (->> (inspect-any map-val)
              (ju/echo)
              (assoc {:isFolder true :title map-key} :children)
              (ju/echo)
              (conj result)
              (ju/echo))) (reduce [] input))))

(defn inspect-coll [input] {:pre [any-coll? input]}
   (ju/log-info "inspect-coll: entered with: input-type =  "(type input) ", input = "input)
        (-> (fn [result [row-count each-coll-item]]  
            (ju/log-info "inspect-coll: each-coll-item = "each-coll-item ",row-count = "row-count ",result = "result)
            (->> (inspect-any each-coll-item)
              (ju/echo)
              (assoc {:isFolder true :title (str "[" row-count "]")} :children)
              (ju/echo)
              (conj result)
              (ju/echo))) (reduce [] (map vector (iterate inc 0) input))))

(defn inspect-oob-array [input]
  (ju/log-info "inspect-oob-array: entered with input = "input)
  (-> (fn [result [row-count each-item]]
        (ju/log-info "inspect-oob-array: result = "result ", each-item = "each-item)
        (conj result {:isFolder false :title (str "[" row-count "] = " each-item)}))
     (reduce [] (map vector (iterate inc 0) input))) )

  (defn inspect-object [obj]
    (let [field-vector (filter-fields obj)]
      (ju/log-info "inspect-object: field-vector = "field-vector)
      (-> (fn [result each-field]
            (ju/log-info "inspect-object: each-field = "each-field ",result = "result)
           (let [
               each-field-name (:name each-field)
               each-field-type (:type each-field)
               each-field-val (ju/instance-field obj (str each-field-name))]
             (ju/log-info "inspect-object: each-field-name = "each-field-name ",each-field-val = " each-field-val ",result = "result)
             (let [output (inspect-any-field each-field each-field-val)]
               (if (not (nil? output))
                 (conj result output) 
                 result))
             )) (reduce [] field-vector))))
  
  
  (defn inspect-object-begin [obj]
    (let [field-vector (filter-fields obj)
          declaring-class (:declaring-class (nth field-vector 0))
          final-op []]
      (ju/log-info "inspect-object-begin: field-vector = "field-vector)
      (conj final-op {:isFolder true 
                      :title declaring-class 
                      :children 
                      (->(fn [result each-field]
                           (ju/log-info "inspect-object-begin: result = "result)
                           (let [
                                 each-field-name (:name each-field)
                                 each-field-type (:type each-field)
                                 each-field-val (ju/instance-field obj (str each-field-name))]
                             (ju/log-info "inspect-object-begin: each-field-name = "each-field-name ", each-field-type = " each-field-type",each-field-val = " each-field-val ",result = "result)
                             (let [output (inspect-any-field each-field each-field-val)]
                               (if (not (nil? output))
                                  (conj result output)
                                  result))
                            ))(reduce [] field-vector))})))

