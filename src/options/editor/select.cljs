(ns options.editor.select)

; create-spec from vals

(defn val->map [idx val]
  ;; convert spec like 
  ;; [:blue :green :red]
  ;; to a spec with :id and :name
  {:id (str (inc idx))
   :name (str val)
   :val val})

(defn vals->spec [vals]
  (map-indexed val->map vals))

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
        current-val-js (if (map? (first spec))
                         (clj->js current-val)
                         (let [dict2 (into {} (map (juxt :val identity) normalized-spec))]
                           (->> current-val
                                (get dict2)
                                (:id)
                                (clj->js))))]
    ;(println "select val: " current-val)
    (into  [:select {:class class
                     :style style
                     :value current-val-js
                     :on-change (fn [e]
                                  (let [id (-> e .-target .-value)
                                        entry (get dict id)
                                        v (:val entry)]
                                    ;(println "entry: " entry)
                                    ;(println "setting select to id: " id-str "val: " v)
                                    (set-fn v)))}]
           (map entry->option normalized-spec))))


(defn editor-select-multiple [{:keys [set-fn options]} current-val]
  (let [{:keys [class style spec]
         :or {class ""
              style {}
              spec []}} options
        normalized-spec (normalize-spec spec)
        dict (into {}
                   (map (juxt :id identity) normalized-spec))
        current-val-js (if (map? (first spec))
                          (clj->js current-val)
                          (let [dict2 (into {} (map (juxt :val identity) normalized-spec))]
                            ;(println "dict2: " dict2)
                            (->> current-val
                                 (map #(get dict2 %))
                                 (map :id)
                                 (into [])
                                 (clj->js))))]
    ;(println "multi-select val: " current-val "val-js: " current-val-js)
    (into  [:select {:class class
                     :style style
                     :value current-val-js
                     ; using default-value, and not value fixed a problem, where selecting 
                     ; items with the mouse would raise two events.
                     ;:default-value current-val-js 
                     :multiple true
                     ;:size 15
                     ;:on-mouse-up (fn [_]
                     ;               ;(when @select-ref
                     ;                ; (reset! finalized-selection (vec (get-selected-values {:target @select-ref})))))
                     ;               (println "on-mouse-up"))
                     ;:on-blur (fn [_]
                     ;           (println "on-blur!"))
                     :on-change (fn [e]
                                  (let [options (.. e -target -options)
                                        selected (for [i (range (.-length options))
                                                       :let [opt (.item options i)]
                                                       :when (.-selected opt)]
                                                   (.-value opt))
                                        ;_ (println "multiselect selected: " selected)
                                        v (->> selected
                                               (map #(get dict %))
                                               (map :val)
                                               (into []))]
                                    ;(println "setting multiselect to: " (pr-str v))
                                    (set-fn v)
                                    ;(println "bongo")
                                    (.preventDefault e)
                                    (.stopPropagation e)
                                    ))}]
           (map entry->option normalized-spec))))



