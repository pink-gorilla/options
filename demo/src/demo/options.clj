(ns demo.options
  (:require
   [options.core :refer [apply-option apply-options ui-state]]))


(def state
  {:asset nil
   :trailing-n nil
   :st {:atr-n nil
        :percentile nil}})

(apply-option state [:trailing-n 1000])


(def state2 
(apply-options state
               [[:trailing-n 1000]
                [:asset "EURUSD"]
                [[:st :atr-n] 50]
                [[:st :percentile] 3]])
  
  )

state2

(def options [{:type :select
               :path [:asset],
               :name "asset",
               :spec
               ["EUR/USD" "USD/CHF" "GBP/USD" "USD/SEK" "USD/NOK" "USD/CAD" "USD/JPY"]}
              {:type :select :path [:trailing-n], :name "trailing#", :spec [2 5 10 20 30 50 80 100 120 150]}
              {:type :select :path [:st :atr-n], :name "dATR#", :spec [5 10 20 30]}
              {:type :select :path [:st :percentile], :name "dPercentile", :spec [10 20 30 40 50 60 70 80 90]}])


(ui-state state2 options)



