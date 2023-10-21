(ns demo.page.controls
  (:require
   [reagent.core :as r]
   [options.edit :as edit]))

(defn h1 [s]
  [:<>
   [:hr]
   [:br]
   [:h1 {:class "pt-5 pb-5 text-xl text-bold text-blue-700"} s]])

(def state-pet (r/atom :cat))
(def state-bool (r/atom true))

(defn page-controls [_]
  [:div
   [:a {:href "/"}
    [:p "goto options"]]

   [h1 "state"]
   [:div " pet: " (pr-str @state-pet)
    " bool: " (pr-str @state-bool)]

   [edit/bool {:set-fn #(reset! state-bool %)
               :options {:class "bg-red-300 p-2"}}
    @state-bool]

   [edit/select
    {:set-fn #(reset! state-pet %)
     :options {:class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"
               :spec [:cat :dog :parrot :hamster]}}
    @state-pet]])