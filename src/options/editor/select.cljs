(ns options.editor.select)

; create-spec from vals

(defn val->map [idx val]
  ;; convert spec like 
  ;; [:blue :green :red]
  ;; to a spec with :id and :name
  {:id (str idx)
   :name (str val)
   :val val})

(defn vals->spec [vals]
  (map-indexed val->map vals))

; create-spec from spec

(defn add-val [{:keys [id] :as opts}]
  (assoc opts :val id :id (str id)))

(defn spec->spec [specs]
  (map add-val specs))

; html helper

(defn entry->option [current-val {:keys [id name val]}]
  ;; works for spec like:
  ;; [{:id 1 :name "Batman"}
  ;;  {:id 2 :name "Robin"}
  ;;  {:id 3 :name "Harry Potter"}]
  (if (= current-val val)
    [:option {:value id :selected true} name]
    [:option {:value id} name]))

(defn get-id [spec id]
  (println "get-id: " id " in spec: " spec)
  (-> (filter #(= id (:id %)) spec)
      first))

(defn editor-select [{:keys [set-fn options]} current-val]
  (let [{:keys [class spec]
         :or {class ""
              spec []}} options
        mapped? (map? (first spec))
        spec (if mapped?
               (spec->spec spec)
               (vals->spec spec))]
    (into  [:select {:class class
                     :on-change (fn [e]
                                  (let [id-str (-> e .-target .-value)
                                        entry (get-id spec id-str) 
                                        v (:val entry)]
                                    ;(println "entry: " entry)
                                    ;(println "setting select to id: " id-str "val: " v)
                                    (set-fn v)))}]
           (map #(entry->option current-val %) spec))))

(defn select? [spec]
  (or (vector? spec)
      (seq? spec)))




