(ns options.editor.bool)

(defn editor-bool [{:keys [set-fn options]} current-val]
  (let [{:keys [class style]
         :or {class ""
              style {}}} options]
    [:input {:class class
             :style style
             :type "checkbox"
             :checked current-val
             :on-change (fn [e]
                          (let [v (-> e .-target .-checked)]
                            ;(println "setting checkbox to: " v)
                            (set-fn v)))}]))