(ns demo.page.options
  (:require
   [options.core :refer [options-ui]]))

(defn page-options [_]
  [:div.grid.grid-cols-2

   [options-ui {:class "bg-blue-300 grid grid-cols-2"
                :style {;:width "50vw"
                        ;:height "40vh"
                        :border "3px solid green"}}
    {:current {; select
               :year 2023
               :client 2
               :pet :hamster
               ; bool
               :run-parallel true
               :debug? false
               ; string
               :search ""}
     :options {:year {:name "Year"
                      :spec (range 2018 2024)}
               :client {:name "Client"
                        :spec [{:id 1 :name "Batman"}
                               {:id 2 :name "Robin"}
                               {:id 3 :name "Harry Potter"}
                               {:id 4 :name "Dumbledor"}
                               {:id 5 :name "The Hulk"}]
                        :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
               :pet {:name "Pet"
                     :spec [:cat :dog :parrot :hamster]
                     :class "placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}

               :run-parallel {:name "Run in Parallel?"
                              :spec :bool
                              :class "pt-0 px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}
               :debug? {:name "Debug Mode"
                        :spec :bool}
               :search {:name "SearchBox"
                        :spec :string
                        :class "px-2 py-1 placeholder-gray-400 text-gray-700 relative bg-white bg-white rounded text-sm border border-gray-400 outline-none focus:outline-none focus:shadow-outline"}}}]])

