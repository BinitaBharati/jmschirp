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


(defn filter-fields [obj top-pkg-name]
  (ju/log-info "filter-fields: entered for obj-type - " (type obj) ", obj = "obj)
  (if (ju/klass-valid-for-inspection? top-pkg-name (.getName (class obj)))
      (->> 
        (cr/reflect obj :ancestors true)
        (ju/echo-wit-msg-in-tl "#####")
        ;{:bases #{java.io.Serializable java.lang.Object}, :flags #{:public}, :members #{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}}
        (:members)
        ;(ju/echo);#{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}
        (filter 
          (fn [each] (instance? clojure.reflect.Field each)))
        ;(ju/echo-wit-msg "****");(#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}})
        (filter 
          (fn [each] (let [flags-set (:flags each)
                           ]
                       (and (not (or (every? flags-set [:private :static :final]) ;(and (contains? flags-set :private) (contains? flags-set :static) (contains? flags-set :final))
                                     (every? flags-set [:public :static :final])
                                     (.isInterface (class obj))
                                     ;(re-seq #"^java\.|^javax\.|^clojure\." (.getName (class obj)))
                                     )) 
                            (ju/klass-valid-for-inspection? top-pkg-name (.getName (class obj)))))))
        (ju/echo-wit-msg-in-tl "filter-fields: exiting with ")
        (into [])
        (ju/echo-wit-msg-in-tl "filter-fields: final exit"))
      []  ;else return with empty vector
    )
)


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
     (and (any-map? temp) (seq temp))
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
                       (conj [(symbol(str (.getName (.getClass key)) ","))])
                       (ju/echo))) 
                   value)                      
            :else
            (recur (-> (conj result (type temp))
                    (ju/echo)
                    (conj [(symbol (str (.getName (.getClass key)) ","))])  ;using symbol, else value coming within double quotes.dunno why? TODO: chk why !
                    (ju/echo)) value))))
     (and (any-coll? temp) (seq temp))
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
                (recur (conj (conj result (type temp)) []) first-item))))
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
         (str field-name (conj result (type temp))))
         ))))))

(defn resolve-oob-ary-type [klass-name]
  (ju/log-info "resolve-oob-ary-type: entered with - "klass-name)
(cond 
  (= klass-name "[Z") "[array-boolean]"
  (= klass-name "[B") "[array-byte]"
  (= klass-name "[C") "[array-char]"
  (= klass-name "[D") "[array-double]"
  (= klass-name "[F") "[array-float]"
  (= klass-name "[I") "[array-int]"
  (= klass-name "[J") "[array-long]"
  (= klass-name "[S") "[array-short]"
  (.startsWith klass-name "[L") ;OOB Object Array
  (str "[array-" (.substring klass-name (+ (.indexOf klass-name "[L") 2)) "]")
  :else klass-name
  )  
)

(defn inspect-any-field [each-field input top-pkg-name] 
  (ju/log-info "inspect-any-field: entered with input = "input ", top-pkg-name = "top-pkg-name)
  (when (not (nil? input))
    (cond
      (any-map? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-map input top-pkg-name)}) 
      (any-coll? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-coll input top-pkg-name)})
      (any-array? (class input))
      (do (if (str-or-num-or-char? (nth input 0))  ;oob array
            (do (ju/log-info "inspect-any-field: str-or-num-or-char array found !")
              {:isFolder true :title (str (:name each-field) (resolve-oob-ary-type (.getName(class input)))) :children  (inspect-oob-array input)})       
            (do (ju/log-info "inspect-any-field: obj array found")  ;custom obj array found
              {:isFolder true :title (str (:name each-field) "[array-" (.getName (.getClass (first input))) "]") :children  (inspect-coll (into [] input) top-pkg-name)})))
      (or (str-or-num-or-char? input) (is-boolean? input))
	    ;{:isFolder false :title (str (:name each-field) "(" (type input) ") = " input)}
     {:isFolder false :title (str (:name each-field) "[" (:type each-field) "] = " input)}     
;     (is-boolean? input)
;     (do
;       {:isFolder false :title (str (:name each-field) " = " input)})
     (.startsWith (.getName (.getClass input)) "java")  ;OOB input found!
      (do 
        (ju/log-info "inspect-any-field: OOB java field found!")
        {:isFolder false :title (str (:name each-field) "[" (:type each-field) "] = " input)})
     :else  ;Object
     (if (ju/klass-valid-for-inspection? top-pkg-name (.getName (.getClass input)))
       {:isFolder true :title (resolve-container-title each-field input) :children (inspect-object input top-pkg-name)}
       nil
      )
    )))

(defn inspect-any [input top-pkg-name]
  (ju/log-info "inspect-any: entered with input = "input)
  (cond
    (any-map? input)
    (inspect-map input top-pkg-name)
    (any-coll? input)
    (inspect-coll input top-pkg-name)
    ;    (str-or-num-or-char? input)
    (or (str-or-num-or-char? input) (is-boolean? input))
    [input] 
    (.startsWith (.getName (.getClass input)) "java")  ;OOB input found!
    [input]
    :else  ;Object
    (inspect-object input top-pkg-name)))

(defn inspect-map [input top-pkg-name] {:pre [(any-map? input)]}
   (ju/log-info "inspect-map: entered with "input)
   (do
      (-> (fn [result [map-key map-val]]  
            (ju/log-info "inspect-map: map-key = "map-key ",map-val = "map-val ",resullt = "result)
            (->> (inspect-any map-val top-pkg-name)
              (ju/echo)
              (assoc {:isFolder true :title map-key} :children)
              (ju/echo)
              (conj result)
              (ju/echo))) (reduce [] input))))

(defn inspect-coll [input top-pkg-name] {:pre [any-coll? input]}
   (ju/log-info "inspect-coll: entered with: input-type =  "(type input) ", input = "input)
        (-> (fn [result [row-count each-coll-item]]  
            (ju/log-info "inspect-coll: each-coll-item = "each-coll-item ",row-count = "row-count ",result = "result)
            (->> (inspect-any each-coll-item top-pkg-name)
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
     (reduce [] (map vector (iterate inc 0) input))))


(defn inspect-object [obj top-pkg-name]
  (ju/log-info "inspect-object: entered with "obj ", obj class = " (.getName (class obj)))
  (let [field-vector (filter-fields obj top-pkg-name)]
    (ju/log-info "inspect-object: field-vector = "field-vector)
    (-> (fn [result each-field]
          (ju/log-info "inspect-object: each-field = "each-field ",result = "result)
          (let [each-field-name (:name each-field)
                each-field-type (:type each-field)
                field-declaring-class (Class/forName (name(:declaring-class each-field)))
                each-field-val (ju/instance-field obj  field-declaring-class (str each-field-name))]
             (ju/log-info "inspect-object: each-field-name = "each-field-name ",each-field-val = "each-field-val, "result = "result)
            (let [output (inspect-any-field each-field each-field-val top-pkg-name)]
              (if (not (nil? output))
                (conj result output) 
                result))
            )) (reduce [] field-vector))))
  
  
(defn inspect-object-begin [obj]
  (ju/log-info "inspect-object-begin: entered with "obj)
  (let [top-pkg-name (ju/top-pkg-structure (.getName (.getClass obj)))
        field-vector (filter-fields obj top-pkg-name)
        declaring-class (:declaring-class (nth field-vector 0))
        final-op []]
    (ju/log-info "inspect-object-begin: declaring-class = "declaring-class ", final-op = "final-op ", top-pkg-name = "top-pkg-name)
    (conj final-op {:isFolder true 
                    :title declaring-class 
                    :children 
                    (->(fn [result each-field]
                         (ju/log-info "inspect-object-begin: result = "result)
                         (let [
                               each-field-name (:name each-field)
                               each-field-type (:type each-field)
                               field-declaring-class (Class/forName (name (:declaring-class each-field)))
                               ;field-declaring-class (Class/forName (name each-field-type))
                               each-field-val (ju/instance-field obj  field-declaring-class (str each-field-name))
                               ]
                           (ju/log-info "inspect-object-begin: each-field-name = "each-field-name ",each-field-type = " each-field-type",each-field-val = " each-field-val ",result = "result )
                           (let [output (inspect-any-field each-field each-field-val top-pkg-name)]
                             (if (not (nil? output))
                                (conj result output)
                                result))
                          ))(reduce [] field-vector))})))

