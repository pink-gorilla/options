(ns demo.page.core
  (:require
   [reagent.core :as r]
   [options.core :refer [options-ui]]))

(def state (r/atom {}))

(def config
  {:state state
   :current {; select
             :year 2023
             :client 2
             :vendor "4"
             :pet :hamster
             :temperature 36.4
             ; bool
             :run-parallel true
             [:environment :enabled] true
             ; string
             :search ""
             ; view
             :msg "hello!"}
   :options [{:type :select
              :path :year
              :name "Year"
              :spec (range 2018 2024)}
             {:type :select
              :path :client
              :name "Client"
              :spec [{:id 1 :name "Batman"}
                     {:id 2 :name "Robin"}
                     {:id 3 :name "Harry Potter"}
                     {:id 4 :name "Dumbledor"}
                     {:id 5 :name "The Hulk"}]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:type :select
              :path :vendor
              :name "Vendor"
              :spec [{:id "1" :name "Batman"}
                     {:id "2" :name "Robin"}
                     {:id "3" :name "Harry Potter"}
                     {:id "4" :name "Dumbledor"}
                     {:id "5" :name "The Hulk"}]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:type :select
              :path :pet
              :name "Pet"
              :spec [:cat :dog :parrot :hamster]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:path :temperature
              :name "Temperature"
              :spec [36.0 36.4 36.5 36.6 36.7 36.8 39.3]
              :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:type :bool
              :path :run-parallel
              :name "RunParallel?"
              :class "pt-0 px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:type :bool
              :path [:environment :enabled] 
              :name "EnvEnabled?"}
             {:type :string
              :path :search
              :name "SearchBox"
              :class "px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
             {:type :button
              :name "Go!"
              :class "bg-blue-500 hover:bg-blue-700 text-white font-bold rounded" ; py-2 px-4
              :on-click #(js/alert "yeah!")}
             {:type :view
              :path :msg
              :name "view"}
             {:name "bad"
              :spec :view2}]})

(defn h1 [s]
  [:<>
   [:hr]
   [:br]
   [:h1 {:class "pt-5 pb-5 text-xl text-bold text-blue-700"} s]])

(defn page [_]
  [:div

   [:a {:href "/controls"} [:p "goto controls"]]
   [:a {:href "/quanta"} [:p "goto quanta"]]

   [h1 "state"]
   [:div (pr-str @state)]

   [h1 "unstyled"]
   [options-ui {:class "bg-blue-300 options-debug"
                :style {:width "50vw"
                         ;:height "40vh"
                        }}config]

   [h1 "label-top"]
   [options-ui {:class "bg-blue-300 options-debug options-label-top"
                :style {:width "50vw"
                         ;:height "40vh"
                        }}config]

   [h1 "label-left"]
   [options-ui {:class "bg-blue-300 options-debug options-label-left"
                :style {:width "50vw"
                         ;:height "40vh"
                        }}config]

   [h1 "label-left-small"]
   [options-ui {:class "bg-blue-300 options-debug options-label-left"
                :style {:width "20vw"
                            ;:height "40vh"
                        }}config]

   [h1 "label-left-big"]
   [options-ui {:class "bg-blue-300 options-debug options-label-left"
                :style {:width "80vw"
                               ;:height "40vh"
                        }}config]])

