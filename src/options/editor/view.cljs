(ns options.editor.view)

(defn editor-view [{:keys [options]} current-val]
  (let [{:keys [class] 
         :or {class ""}} 
        options]
    [:span {:class class} (pr-str current-val)]))