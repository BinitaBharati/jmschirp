(ns jmschirp.msg-browser-helper
  (:import (clojure.lang Reflector) (java.util Collection))
  (:require [clojure.reflect :as cr]
            [clojure.pprint :as cpp]
            [jmschirp.util :as ju]))


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
     (and (ju/any-map? temp) (seq temp))
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
     (and (ju/any-coll? temp) (seq temp))
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
      (ju/any-map? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-map input top-pkg-name)}) 
      (ju/any-coll? input)
      (do
        {:isFolder true :title (resolve-container-title each-field input) :children (inspect-coll input top-pkg-name)})
      (ju/any-array? (class input))
      (do (if (ju/str-or-num-or-char? (nth input 0))  ;oob array
            (do (ju/log-info "inspect-any-field: str-or-num-or-char array found !")
              {:isFolder true :title (str (:name each-field) (resolve-oob-ary-type (.getName(class input)))) :children  (inspect-oob-array input)})       
            (do (ju/log-info "inspect-any-field: obj array found")  ;custom obj array found
              {:isFolder true :title (str (:name each-field) "[array-" (.getName (.getClass (first input))) "]") :children  (inspect-coll (into [] input) top-pkg-name)})))
      (or (ju/str-or-num-or-char? input) (ju/is-boolean? input))
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
    (ju/any-map? input)
    (inspect-map input top-pkg-name)
    (ju/any-coll? input)
    (inspect-coll input top-pkg-name)
    ;    (str-or-num-or-char? input)
    (or (ju/str-or-num-or-char? input) (ju/is-boolean? input))
    [input] 
    (.startsWith (.getName (.getClass input)) "java")  ;OOB input found!
    [input]
    :else  ;Object
    (inspect-object input top-pkg-name)))

(defn inspect-map [input top-pkg-name] {:pre [(ju/any-map? input)]}
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

(defn inspect-coll [input top-pkg-name] {:pre [ju/any-coll? input]}
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
  (let [field-vector (ju/filter-fields obj top-pkg-name)]
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
        field-vector (ju/filter-fields obj top-pkg-name)
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

