(ns jmschirp.util
   (:require ;[basil.public :as bp]
     [clostache.parser :as cp]
             ;[taoensso.timbre :as timbre]
             [clojure.tools.logging :as ctl]
             [clojure.reflect :as cr]
             )
   (:import (java.io FileNotFoundException)
            (java.io File)
            (clojure.lang Reflector)))


(defn echo
  [x]
  (println "[Echo]" x)
  x)

(defn echo-wit-msg
  [x msg]
  (println "[Echo]" msg x)
  x)

(defn echo-wit-msg-in-tl
  [msg x]
  (println "[Echo]" msg x)
  x)

(defn log-info [ & input] (ctl/info (apply str input)))

(defn safe-read-string [input]
  (if(empty? input)
    []
    (read-string input)))

(defn add-id [input]
  "Add id to the input. Id is used in the browser to identify each DOM element.Input is a vector of maps."
 (loop [index 0
        result []]
   (if (= index (count input))
     result
   (do (recur (inc index) (conj result (conj (nth input index) {:id index})))))))

(def default-supported-vd [{:type "EMS",:version "7.0",:provider-ns "jmschirp.adaptor.tibco"}
                           {:type "ActiveMQ",:version "5.8.0" :provider-ns "jmschirp.adaptor.activemq"}])

(defn mkdir-p [abs-dir]
  (log-info "mkdir-p: entered with abs-dir = "abs-dir)
  (let [file (File. abs-dir)]
    (when-not (.exists file)
      (.mkdir file))))

(defn read-file [input]
  (try (->> (get input :path)
    (str (System/getProperty "user.home"))
    slurp
    echo
    safe-read-string
    echo)
    (catch FileNotFoundException fnfe
      (if (= (get input :type) 0)
      []
      (do 
        (mkdir-p (str (System/getProperty "user.home") "/.jmschirp"))
        (spit (str (System/getProperty "user.home") "/.jmschirp/supported-vendors.clj") (pr-str default-supported-vd))
        default-supported-vd)))))

(defn list-conn []
  (read-file {:type 0 :path "/.jmschirp/vendor-info.clj"}))

(defn get-valid-vd []
  (read-file {:type 1 :path "/.jmschirp/supported-vendors.clj"}))

(defn get-conn-info [input]
  (->> (read-file {:type 0 :path "/.jmschirp/vendor-info.clj"})
    (filter (fn [each] (= (get each :name) (get input :name))))
    first
    echo))

(defn get-supported-vd-info [input]
  (-> (fn [each] (= (get input :type) (get each :type)))
    (filter default-supported-vd)
    (first)))

(defn instance-field2 [target field-name] {:pre [(string? field-name)]}
  (Reflector/getInstanceField target field-name))

(defn get-field [target-class field-name] {:pre [(string? field-name) (instance? Class target-class)]}
  (log-info (str "get-field: entered with " field-name " " target-class))
  (let [all-fields (.getDeclaredFields target-class)]
    (log-info (str "get-field: all-fields " all-fields " all-fields-length " (count all-fields)))
    (some (fn [each-java-field] 
            (log-info (str "get-field: each-java-field name is *" (.getName each-java-field) "**"field-name "*" (type field-name) "***"))
            (when (= (.getName each-java-field) (str field-name))
              (log-info (str "match found! "field-name))
              (.setAccessible each-java-field true)
              each-java-field))
          all-fields))
  )


(defn instance-field [target field-class field-name]{:pre [(instance? Class field-class) (string? field-name)]}
  (log-info (str "instance-field: entered with field-class = " field-class ", field-name = "field-name))
  (let [target-class field-class
        java-field (get-field target-class field-name)]
     (cond
       (not (nil? java-field))
       (.get java-field target)
       :else (log-info "OOPs! - field-name = "field-name " not found in class = "field-class))
    ))

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


(def country-code-domains 
  ["ac" "ad" "ae" "af" "ag" "ai" "al" "am" "an" "ao" "aq" "ar" "as" "at" "au" "aw" "ax" "az" "ba" "bb" "bd" "be" "bf"
   "bg" "bh" "bi" "bj" "bm" "bn" "bo" "bs" "bt" "bv" "bw" "by" "bz" "ca" "cc" "cd" "cf" "cg" "ch" "ci" "ck" "cl" "cm"
   "cn" "co" "cr" "cs" "cu" "cv" "cx" "cy" "cz" "dd" "de" "dj" "dk" "dm" "do" "dz" "ec" "ee" "eg" "eh" "er" "es" "et"
   "eu" "fi" "fj" "fk" "fm" "fo" "fr" "ga" "gb" "gd" "ge" "gf" "gg" "gh" "gl" "gm" "gn" "gp" "gq" "gr" "gs" "gt" "gu"
   "gw" "gy" "hk" "hm" "hn" "hr" "ht" "hu" "id" "ie" "il" "im" "in" "io" "iq" "ir" "is" "it" "je" "jm" "jo" "jp" "ke"
   "kg" "kh" "ki" "km" "kn" "kp" "kr" "kw" "ky" "kz" "la" "lb" "lc" "li" "lk" "lr" "lt" "lu" "lv" "ly" "ma" "mc" "md"
   "me" "mg" "mh" "mk" "ml" "mm" "mn" "mo" "mp" "mq" "mr" "ms" "mt" "mu" "mv" "mw" "mx" "my" "mz" "na" "nc" "ne" "nf"
   "ng" "ni" "nl" "no" "np" "nr" "nu" "nz" "om" "cg" "pa" "pe" "pf" "pg" "ph" "pk" "pl" "pm" "pn" "pr" "ps" "pt" "pw"
   "py" "qa" "re" "ro" "rs" "ru" "rw" "sa" "sb" "sc" "sd" "se" "sg" "sh" "si" "sj" "sk" "sl" "sm" "sn" "so" "sr" "ss"
   "st" "su" "sv" "sx" "sy" "tc" "td" "tf" "tg" "th" "tj" "tk" "tl" "tm" "tn" "to" "tp" "tr" "tv" "tw" "tz" "ua" "ug"
   "uk" "us" "uy" "uz" "va" "vc" "ve" "vg" "vi" "vn" "vu" "wf" "ws" "ye" "yt" "yu" "za" "zm" "zw"])

(def generic-top-domains
  ["aero" "asia" "biz" "cat" "com" "coop" "info" "int" "jobs" "mobi" "museum" "name" "net" "org" "post" "pro" "tel"
   "travel" "xxx" "edu" "gov" "co"]
)

(def cc-and-td #"(^ac|^ad|^ae|^af|^ag|^ai|^al|^am|^an|^ao|^aq|^ar|^as|^at|^au|^aw|^ax
|^az|^ba|^bb|^bd|^be|^bf|^bg|^bh|^bi|^bj|^bm|^bn|^bo|^bs|^bt|^bv|^bw|^by|^bz|^ca|^cc|^cd|^cf
|^cg|^ch|^ci|^ck|^cl|^cm|^cn|^co|^cr|^cs|^cu|^cv|^cx|^cy|^cz|^dd|^de|^dj|^dk|^dm|^do|^dz|^ec
|^ee|^eg|^eh|^er|^es|^et|^eu|^fi|^fj|^fk|^fm|^fo|^fr|^ga|^gb|^gd|^ge|^gf|^gg|^gh|^gl|^gm|^gn
|^gp|^gq|^gr|^gs|^gt|^gu|^gw|^gy|^hk|^hm|^hn|^hr|^ht|^hu|^id|^ie|^il|^im|^in|^io|^iq|^ir|^is
|^it|^je|^jm|^jo|^jp|^ke|^kg|^kh|^ki|^km|^kn|^kp|^kr|^kw|^ky|^kz|^la|^lb|^lc|^li|^lk|^lr|^lt
|^lu|^lv|^ly|^ma|^mc|^md|^me|^mg|^mh|^mk|^ml|^mm|^mn|^mo|^mp|^mq|^mr|^ms|^mt|^mu|^mv|^mw|^mx
|^my|^mz|^na|^nc|^ne|^nf|^ng|^ni|^nl|^no|^np|^nr|^nu|^nz|^om|^cg|^pa|^pe|^pf|^pg|^ph|^pk|^pl
|^pm|^pn|^pr|^ps|^pt|^pw|^py|^qa|^re|^ro|^rs|^ru|^rw|^sa|^sb|^sc|^sd|^se|^sg|^sh|^si|^sj|^sk
|^sl|^sm|^sn|^so|^sr|^ss|^st|^su|^sv|^sx|^sy|^tc|^td|^tf|^tg|^th|^tj|^tk|^tl|^tm|^tn|^to|^tp
|^tr|^tv|^tw|^tz|^ua|^ug|^uk|^us|^uy|^uz|^va|^vc|^ve|^vg|^vi|^vn|^vu|^wf|^ws|^ye|^yt|^yu|^za
|^zm|^zw)(\.)(aero|asia|biz|cat|com|coop|info|int|jobs|mobi|museum|name|net|org|post|pro|tel
|travel|xxx|edu|gov|co)")

(def cc #"(^ac|^ad|^ae|^af|^ag|^ai|^al|^am|^an|^ao|^aq|^ar|^as|^at|^au|^aw|^ax|^az|^ba|^bb|^bd
|^be|^bf|^bg|^bh|^bi|^bj|^bm|^bn|^bo|^bs|^bt|^bv|^bw|^by|^bz|^ca|^cc|^cd|^cf|^cg|^ch|^ci|^ck|^cl
|^cm|^cn|^co|^cr|^cs|^cu|^cv|^cx|^cy|^cz|^dd|^de|^dj|^dk|^dm|^do|^dz|^ec|^ee|^eg|^eh|^er|^es|^et
|^eu|^fi|^fj|^fk|^fm|^fo|^fr|^ga|^gb|^gd|^ge|^gf|^gg|^gh|^gl|^gm|^gn|^gp|^gq|^gr|^gs|^gt|^gu|^gw
|^gy|^hk|^hm|^hn|^hr|^ht|^hu|^id|^ie|^il|^im|^in|^io|^iq|^ir|^is|^it|^je|^jm|^jo|^jp|^ke|^kg|^kh
|^ki|^km|^kn|^kp|^kr|^kw|^ky|^kz|^la|^lb|^lc|^li|^lk|^lr|^lt|^lu|^lv|^ly|^ma|^mc|^md|^me|^mg|^mh
|^mk|^ml|^mm|^mn|^mo|^mp|^mq|^mr|^ms|^mt|^mu|^mv|^mw|^mx|^my|^mz|^na|^nc|^ne|^nf|^ng|^ni|^nl|^no
|^np|^nr|^nu|^nz|^om|^cg|^pa|^pe|^pf|^pg|^ph|^pk|^pl|^pm|^pn|^pr|^ps|^pt|^pw|^py|^qa|^re|^ro|^rs
|^ru|^rw|^sa|^sb|^sc|^sd|^se|^sg|^sh|^si|^sj|^sk|^sl|^sm|^sn|^so|^sr|^ss|^st|^su|^sv|^sx|^sy|^tc
|^td|^tf|^tg|^th|^tj|^tk|^tl|^tm|^tn|^to|^tp|^tr|^tv|^tw|^tz|^ua|^ug|^uk|^us|^uy|^uz|^va|^vc|^ve
|^vg|^vi|^vn|^vu|^wf|^ws|^ye|^yt|^yu|^za|^zm|^zw)(\.)")

(def td #"(^aero|^asia|^biz|^cat|^com|^coop|^info|^int|^jobs|^mobi|^museum|^name|^net|^org|^post|^pro|^tel|^travel|^xxx|^edu|^gov|^co)(\.)")

;Ref: http://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html,
;http://stackoverflow.com/questions/2125293/java-packages-com-and-org,
;http://en.wikipedia.org/wiki/List_of_Internet_top-level_domains
;Normal pkging convention would be : 
;country-code-domains.generic-top-domains.<ur org pkgstarts here>
;country-code-domains.<ur org pkgstarts here>
;generic-top-domains.<ur org pkgstarts here>
(defn top-pkg-structure [klass-name]
  (log-info "default-browse-msg-filter: entered for = "klass-name)
  (cond 
    (not (nil? (ffirst (re-seq cc-and-td klass-name))))  ;country-code-domains.<ur org pkgstarts here>
    (let [ match-pkg (ffirst (re-seq cc-and-td klass-name))]
      (log-info "default-browse-msg-filter: match-pkg for cc-and-td = "match-pkg)
      (str (.substring klass-name 0 (.indexOf klass-name "." (inc (.length match-pkg))))))
    (not (nil? (ffirst (re-seq cc klass-name))))  ;country-code-domains.<ur org pkgstarts here>
    (let [ match-pkg (ffirst (re-seq cc klass-name))]
      (log-info "default-browse-msg-filter: match-pkg for cc = "match-pkg)
      (str (.substring klass-name 0 (.indexOf klass-name "." (inc (.length match-pkg))))))
    (not (nil? (ffirst (re-seq td klass-name))))  ;generic-top-domains.<ur org pkgstarts here>
    (let [ match-pkg (ffirst (re-seq td klass-name))]
      (log-info "default-browse-msg-filter: match-pkg for td = "match-pkg)
      (str (.substring klass-name 0 (.indexOf klass-name "." (inc (.length match-pkg))))))
    :else
    (str (.substring klass-name 0 (.indexOf klass-name ".")))
    )
)

(def oob-klass #"^java\.|^javax\.|^clojure\.")

(defn klass-valid-for-inspection? [top-pkg-name klass-name]
 (and (nil? (re-seq oob-klass klass-name))  ;not OOB class
      (.startsWith klass-name top-pkg-name)  ;pkg name matching <ur pkg> name
))

(defn filter-fields [obj top-pkg-name]
  (log-info "filter-fields: entered for obj-type - " (type obj) ", obj = "obj)
  (if (klass-valid-for-inspection? top-pkg-name (.getName (class obj)))
      (->> 
        (cr/reflect obj :ancestors true)
        (echo-wit-msg-in-tl "#####")
        ;{:bases #{java.io.Serializable java.lang.Object}, :flags #{:public}, :members #{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}}
        (:members)
        ;(ju/echo);#{#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Method{:name setIntField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.Integer], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name setListField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.util.List], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getStrField, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getIntField, :return-type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name setStrField, :return-type void, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [java.lang.String], :exception-types [], :flags #{:public}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Method{:name toString, :return-type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Constructor{:name bbharati.jmschirp.test.TestObjMsg_Java, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}} #clojure.reflect.Method{:name getListField, :return-type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :parameter-types [], :exception-types [], :flags #{:public}}}
        (filter 
          (fn [each] (instance? clojure.reflect.Field each)))
        ;(ju/echo-wit-msg "****");(#clojure.reflect.Field{:name strField, :type java.lang.String, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name serialVersionUID, :type long, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private :static :final}} #clojure.reflect.Field{:name listField, :type java.util.List, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}} #clojure.reflect.Field{:name intField, :type java.lang.Integer, :declaring-class bbharati.jmschirp.test.TestObjMsg_Java, :flags #{:private}})
        (filter 
          (fn [each] (let [flags-set (:flags each)]
                       (and (not (or (every? flags-set [:private :static :final]) ;(and (contains? flags-set :private) (contains? flags-set :static) (contains? flags-set :final))
                                     (every? flags-set [:public :static :final])
                                     (.isInterface (class obj))
                                     ;(re-seq #"^java\.|^javax\.|^clojure\." (.getName (class obj)))
                                     )) 
                            (klass-valid-for-inspection? top-pkg-name (.getName (class obj)))))))
        (echo-wit-msg-in-tl "filter-fields: exiting with ")
        (into [])
        (echo-wit-msg-in-tl "filter-fields: final exit"))
      []  ;else return with empty vector
    )
)