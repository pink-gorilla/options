(ns options.editor.select)

; create-spec from vals

(defn val->map [val]
  ;; convert spec like 
  ;; [:blue :green :red]
  ;; to a spec with :id and :name
  {:id (str val)
   :name (str val)
   :val val})

(defn vals->spec [vals]
  (map val->map vals))

; create-spec from spec

(defn add-val [{:keys [id] :as opts}]
  (assoc opts :val id :id (str id)))

(defn spec->spec [specs]
  (map add-val specs))

(defn normalize-spec
  "spec always has to be a vector of elements
   element type:
   map: spec of {:id :name}
   any other type. it is a list of ids which get converted to names with str."
  [spec]
   (if (map? (first spec))
               (spec->spec spec)
               (vals->spec spec)))

(defn entry->option [{:keys [id name]}]
  ;; works for spec like:
  ;; [{:id 1 :name "Batman"}
  ;;  {:id 2 :name "Robin"}
  ;;  {:id 3 :name "Harry Potter"}]
  [:option {:value id} name])


(defn editor-select [{:keys [set-fn options]} current-val]
  (let [{:keys [class style spec]
         :or {class ""
              style {}
              spec []}} options
        normalized-spec (normalize-spec spec)
        dict (into {}
                   (map (juxt :id identity) normalized-spec))
        current-val (str current-val)]
    (println "select val: " current-val)
    (into  [:select {:class class
                     :style style
                     :value current-val
                     :on-change (fn [e]
                                  (let [id (-> e .-target .-value)
                                        entry (get dict id)
                                        v (:val entry)]
                                    ;(println "entry: " entry)
                                    ;(println "setting select to id: " id-str "val: " v)
                                    (set-fn v)))}]
           (map entry->option normalized-spec))))




