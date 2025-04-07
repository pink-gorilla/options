(ns demo.flowy
  (:require
   [missionary.core :as m]
   [options.flowy.core :refer [create! get-edit-flow set-edit-value]]
   [demo.options :refer [state2 options]]
   ))


;(def id 42)
;(create! id state2 options)

(def id 66)

id

@options.flowy.core/running-a

(def ui-flow 
  (get-edit-flow id))

(def main
  (m/reduce 
   (fn [_ x] (prn "ui-data: " x)) 
   nil 
   ui-flow))

(def dispose!
  (main
   #(prn ::success %)
   #(prn ::crash %)))

(set-edit-value id [:asset "EURUSD"])

(set-edit-value id [:asset "USDJPY"])

(set-edit-value id [:trailing-n 10000])

(set-edit-value id [[:st :atr-n] 20])


(dispose!)
