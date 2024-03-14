(ns options.core
  (:require
   [reagent.core :as r]
   ; editors
   [options.editor.view :refer [editor-view]]
   [options.editor.bool :refer [editor-bool]]
   [options.editor.string :refer [editor-string]]
   [options.editor.select :refer [editor-select]]
   [options.editor.button :refer [editor-button]]))

; editors registry

(defonce editors
  (atom {:bool editor-bool
         :string editor-string
         :button editor-button
         :select editor-select
         :view editor-view}))

(defn register-editor [t f]
  (swap! editors assoc t f))

(defn get-editor [t] 
  (or (get @editors t) editor-view))


(defn get-value [state path]
  (when path
    (get @state path)))

(defn set-value [state path v]
  (when path 
    (println "set-value path: " path " value: " v)
    (swap! state assoc path v)))

(defn create-edit-element [state {:keys [path name type] :as options}]
  (let [editor (get-editor type)]
    [:<>
     [:span name] ; <label for= "pet-select" >Choose a pet:</label>
     [editor {:state state
              :set-fn #(set-value state path %)
              :options options}
      (get-value state path)]]))

(defn options-ui [{:keys [class style]}  ; styling
                  {:keys [current state options]
                   :or {state (r/atom current)} :as config}] ; data
  (reset! state current)
  (fn [_styling {:keys [state options] :as config}]
    (into [:div {:style style
                 :class class}]
          (map #(create-edit-element state %) options))))
