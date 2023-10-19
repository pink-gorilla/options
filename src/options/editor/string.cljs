(ns options.editor.string)

(defn editor-string [{:keys [set-fn options]} current-val]
  (let [{:keys [class name] :or {class "" name ""}} options]
    [:input {:class class
             :type "text"
             :value current-val
             :placeholder name
             :on-change (fn [e]
                          (let [v (-> e .-target .-value)]
                            (println "setting checkbox to: " v)
                            (set-fn v)))}]))