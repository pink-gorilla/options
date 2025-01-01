(ns options.editor.view)

(defn editor-view [{:keys [options]} current-val]
  (let [{:keys [class style]
         :or {class ""
              style {}}}
        options]
    [:span {:class class
            :style style} (pr-str current-val)]))