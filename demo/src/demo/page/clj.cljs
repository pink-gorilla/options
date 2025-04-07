(ns demo.page.clj
  (:require
   [reagent.core :as r]
   [options.flowy.core :refer [clj-option-ui]]))


(defn page [_]
  [:div 
   [:hr]
   [:p "view and change server (clj) options"]
   [:p "view/editable options first need to be created, and"]
   [:p "then they can be edited in this ui"]
   [:p "note that the changes made are persisted on the server, so"]
   [:p "a new connected client will see the updated values"]
   
   [clj-option-ui {:id 66
                   :class "options-label-left"
                   :style {:background-color "yellow"
                           :max-height "400px"
                           :max-width "400px"
                                                        ;:min-height "400px"
                                                        ;:min-width "400px"
                           :height "400px"
                           :width "400px"}}]
   
   [:p "note that the same id can be viewed/edited multiple times"]
   [:p "server-side most likely there are running calculations that are depending on this optiosn"]

   [clj-option-ui {:id 66
                   :class "options-label-left"
                   :style {:background-color "blue"
                           :max-height "400px"
                           :max-width "400px"
                                                           ;:min-height "400px"
                                                           ;:min-width "400px"
                           :height "400px"
                           :width "400px"}}]
   
   ]
    
   
  )