(ns options.core
  (:require
   [reagent.core :as r]
   ; editors
   [options.editor.view :refer [editor-view]]
   [options.editor.bool :refer [editor-bool]]
   [options.editor.string :refer [editor-string]]
   [options.editor.select :refer [editor-select select?]]
   [options.editor.button :refer [editor-button]]))

(defn get-editor-fn [{:keys [options] :as config} current-val]
  (let [{:keys [spec]} options]
    (println "get-editor-fn options: " options "current-val: " current-val)
    (cond
      (= spec :bool)
      (editor-bool config current-val)

      (= spec :string)
      (editor-string config current-val)

      (= spec :button)
      (editor-button config current-val)

      (select? spec)
      (editor-select config current-val)

      :else
      (editor-view config current-val))))

(defn edit-element [{:keys [options] :as config} current-val]
  (let [{:keys [name]} options]
    [:<>
     [:span name] ; <label for= "pet-select" >Choose a pet:</label>
     (get-editor-fn config current-val)]))

(defn create-edit-element [state options]
  (let [kw (:path options)
        set-fn (fn [v]
                 (when kw
                   (println "setting state for kw: " kw " to val: " v)
                   (swap! state assoc kw v)))]
    [edit-element {:set-fn set-fn
                   :options options}
     (if kw
       (kw @state)
       nil)]))

(defn options-ui [{:keys [class style] :as styling}  ; styling
                  {:keys [current state options]
                   :or {state (r/atom current)} :as config}] ; data
  (reset! state current)
  (fn [_styling {:keys [current state options] :as config}]
    (into [:div {:style style
                 :class class}]
          (map #(create-edit-element state %) options))))
