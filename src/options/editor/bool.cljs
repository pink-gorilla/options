(ns options.editor.bool)

(defn editor-bool [{:keys [set-fn options]} current-val]
  (let [{:keys [class] :or {class ""}} options]
    [:input {:class class
             :type "checkbox"
             :checked current-val
             :on-change (fn [e]
                          (let [v (-> e .-target .-checked)]
                            ;(println "setting checkbox to: " v)
                            (set-fn v)))}]))