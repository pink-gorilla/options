(ns options.editor.select)

(defn entry->option [current-val {:keys [id name]}]
  ;; works for spec like:
  ;; [{:id 1 :name "Batman"}
  ;;  {:id 2 :name "Robin"}
  ;;  {:id 3 :name "Harry Potter"}]
  (if (= current-val id)
    [:option {:value id :selected true} name]
    [:option {:value id} name]))

(defn spec->map [spec1]
  ;; convert spec like 
  ;; [:blue :green :red]
  ;; to a spec with :id and :name
  {:id spec1
   :name spec1})

(defn type-convert [val1 v]
  (cond
    (number? val1)
    (long v)

    (keyword? val1)
    (keyword v)

    (string? val1)
    v

    :else
    v))

(defn editor-select [{:keys [set-fn options]} current-val]
  (let [{:keys [class spec]
         :or {class ""
              spec []}} options
        option1 (first spec)
        spec (if (map? option1)
               spec
               (map spec->map spec))
        val1 (-> spec first :id)
        otype (type val1)]
    (println "select id type: " otype)
    (into  [:select {:class class
                     :on-change (fn [e]
                                  (let [v (-> e .-target .-value)
                                        v2 (type-convert val1 v)]
                                    (println "setting select to: " v "v2: " v2)
                                    (set-fn v2)))}]
           (map #(entry->option current-val %) spec))))

(defn select? [spec]
  (or (vector? spec)
      (seq? spec)))




