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

(defn editor-with-label [{:keys [value set-fn]}  {:keys [name type] :as options}]
  (let [editor (get-editor type)]
    (if (= type :label)
      ; label only
      [:<>
       [:span]
       [:span.font-bold.font-big name]]
      ; editor
      [:<>
       [:span
        {:style {:text-overflow "clip"  ; "ellipsis" ; ellipsis needs too much space, clip allws to display a little more text
                 :white-space "nowrap" ; nowrap is needed;  otherwise text would flow to next line
                 :overflow "hidden"
                 :display "inline-block"}}

        name] ; <label for= "pet-select" >Choose a pet:</label>
       [editor {:set-fn set-fn
                :options options}
        value]])))

(defn dynamic-editor [{:keys [state get-fn set-fn]} {:keys [path] :as options}]
  (let [value (get-fn @state path)
        set-fn (partial set-fn path)]
    [editor-with-label {:value value :set-fn set-fn} options]))


(defn options-ui2 [{:keys [class style
                           edit
                           state
                           set-fn
                           get-fn]}]
  (let [set-fn (or set-fn 
                   (fn [path v]
                     ;(println "setting " path " to: " v)
                     (swap! state assoc path v)))
        get-fn (or get-fn 
                   (fn [state-val path]
                    (get state-val path)))]
  (into [:div {:style style
               :class class}]
        (map #(dynamic-editor {:state state 
                               :get-fn get-fn
                               :set-fn set-fn} %) edit))))

(defn options-ui [{:keys [class style]}  ; styling
                  {:keys [current state options]
                   :or {state (r/atom current)} :as config}] ; data
  (reset! state current)
  [options-ui2 {:class class
                :style style
                :state state
                :edit options}])