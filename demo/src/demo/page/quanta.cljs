(ns demo.page.quanta
  (:require
   [reagent.core :as r]
   [options.core :refer [options-ui2]]))

(def state (r/atom {[0 :asset] "USD/JPY",
                    [2 :trailing-n] 120,
                    [2 :atr-n] 10,
                    [2 :percentile] 70,
                    [2 :step] 1.0E-4,
                    [4 :max-open-close-over-low-high] 0.3}))

(def options [{:type :select
              :path [0 :asset],
              :name "asset",
              :spec
              ["EUR/USD" "USD/CHF" "GBP/USD" "USD/SEK" "USD/NOK" "USD/CAD" "USD/JPY"
               "AUD/USD" "NZD/USD" "USD/MXN" "USD/ZAR" "EUR/JPY" "EUR/CHF" "EUR/GBP" "GBP/JPY"]}
             {:type :select :path [2 :trailing-n], :name "trailing#", :spec [2 5 10 20 30 50 80 100 120 150]}
             {:type :select :path [2 :atr-n], :name "dATR#", :spec [5 10 20 30]}
             {:type :select :path [2 :percentile], :name "dPercentile", :spec [10 20 30 40 50 60 70 80 90]}
             {:type :label  :name "v2"}
             {:type :select :path [2 :step], :name "dStep", :spec [0.001 1.0E-4 4.0E-5]}
             {:type :select :path [4 :max-open-close-over-low-high], :name "doji-co/lh max", :spec [0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9]}])

(defn h1 [s]
  [:<>
   [:hr]
   [:br]
   [:h1 {:class "pt-5 pb-5 text-xl text-bold text-blue-700"} s]])

(defn page [_]
  [:div
   [:a {:href "/controls"}
    [:p "goto controls"]]

   [h1 "state"]
   [:div (pr-str @state)]

   [h1 "unstyled"]
   [options-ui2 {:class "bg-blue-300 options-debug"
                 :style {:width "50vw"
                         ;:height "40vh"
                        }
                 :edit options
                 :state state
                 } ]
   
   [h1 "labels-left"]
   [options-ui2 {:class "bg-blue-300 options-label-left"
                 :style {:width "50vw"
                            ;:height "40vh"
                         }
                 :edit options
                 :state state}]
   

  [h1 "labels-left-1-col"]
  [options-ui2 {:class "bg-blue-300 options-label-left"
              :style {:width "300px"
                            ;:height "40vh"
                      }
              :edit options
              :state state}]



   ])