(ns options.editor.string)

(defn editor-string [{:keys [set-fn options]} current-val]
  (let [{:keys [class style name]
         :or {class ""
              style {}
              name ""}} options]
    [:input {:class class
             :style style
             :type "text"
             :value current-val
             :placeholder name
             :on-change (fn [e]
                          (let [v (-> e .-target .-value)]
                            ;(println "setting string to: " v)
                            (set-fn v)))}]))