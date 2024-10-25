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

(defn create-edit-element [{:keys [set-fn get-fn]}  {:keys [path name type] :as options}]
  (let [editor (get-editor type)]
    [:<>
     [:span name] ; <label for= "pet-select" >Choose a pet:</label>
     [editor {:set-fn (partial set-fn path)
              :options options}
      (get-fn path)]]))

(defn options-ui2 [{:keys [class style
                           edit
                           state
                           set-fn
                           get-fn]
                    :or {set-fn (fn [path v]
                                 ;(println "setting " path " to: " v)
                                  (swap! state assoc path v))
                         get-fn (fn [path]
                                  (get @state path))}}]
  (into [:div {:style style
               :class class}]
        (map #(create-edit-element {:set-fn set-fn
                                    :get-fn get-fn} %) edit)))

(defn options-ui [{:keys [class style]}  ; styling
                  {:keys [current state options]
                   :or {state (r/atom current)} :as config}] ; data
  (reset! state current)
  [options-ui2 {:class class
                :style style
                :state state
                :edit options}])