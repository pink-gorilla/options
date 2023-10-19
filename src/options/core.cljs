(ns options.core
  (:require
   [reagent.core :as r]
   ; editors
   [options.editor.view :refer [editor-view]]
   [options.editor.bool :refer [editor-bool]]
   [options.editor.string :refer [editor-string]]
   [options.editor.select :refer [editor-select select?]]))

(defn get-editor-fn [{:keys [options] :as config} current-val]
  (let [{:keys [spec]} options]
    (println "get-editor-fn options: " options "current-val: " current-val)
    (cond
      (= spec :bool)
      (editor-bool config current-val)

      (= spec :string)
      (editor-string config current-val)

      (select? spec)
      (editor-select config current-val)

      :else
      (editor-view config current-val))))

(defn edit-element [{:keys [options] :as config} current-val]
  (let [{:keys [name]} options]
    [:<>
     [:span name] ; <label for= "pet-select" >Choose a pet:</label>
     (get-editor-fn config current-val)]))

(defn edit-params [options set-fn kw]
  {:set-fn #(set-fn kw %)
   :options  (get-in options [:options kw])})

(defn create-edit-element [state options set-fn kw]
  [edit-element (edit-params options set-fn kw) (kw @state)])

(defn options-ui [{:keys [class style] :as styling}  ; styling
                  {:keys [current] :as options}] ; data
  (let [state (r/atom current)
        set-fn (fn [kw v]
                 (println "setting state for kw: " kw " to val: " v)
                 (swap! state assoc kw v))]
    (fn [_styling options]
      (into [:div {:style style
                   :class class}
             [:div "state: "]
             [:div (pr-str @state)]]
            (map #(create-edit-element state options set-fn %)
                 (keys (:current options)))))))
