(ns options.editor.view)

(defn editor-view [{:keys [set-fn options]} current-val]
  (let [{:keys [class] :or {class ""}} options]
    [:span {:class class} current-val]))