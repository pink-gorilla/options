(ns options.flowy.core
  (:require
   [reagent.core :as r]
   [missionary.core :as m]
   [flowy.reflower :refer [task flow]]
   [options.core :as oui]))

(defn create-subscriber [id]
  (println "subscribing to option-ui: " id)
  (let [state-a (r/atom {})
        options-a (r/atom [])
        option-flow (flow 'options.flowy.core/get-edit-flow id)
        dispose! (let [_ (println "terminating subscribing to option-ui: " id)
                       task (m/reduce (fn [_r v]
                                        (println "option-subscriber rcvd: " v)
                                        (when (:state v)
                                          (reset! state-a (:state v)))
                                        (when (:options v)
                                          (reset! options-a (:options v)))
                                        nil) nil option-flow)]
                   (task
                    #(println "clj-option subscriber completed: " %)
                    #(println "clj-option subscriber crashed. error: " %)))]
    [state-a options-a dispose!]))

(defn set-edit-value [id path-value-vec]
  (let [_ (println "setting option " id " data: " path-value-vec)
        t (task 'options.flowy.core/set-edit-value id path-value-vec)]
    (t
     #(println "set-option completed. value: " %)
     #(println "set-option crashed. error: " %))))

(defn debug-ui [state-a options-a]
  [:div
   [:hr]
   [:div "state" (pr-str @state-a)]
   [:hr]
   [:div "options" (pr-str @options-a)]
   [:hr]])

(defn clj-option-ui [{:keys [id class style]}]
  (r/with-let [[state-a options-a dispose!] (create-subscriber id)]
    (if (and @state-a @options-a)
      [oui/options-ui2 {:class class
                        :style style
                        :edit @options-a
                        :state state-a
                        :set-fn (fn [path v]
                                  (println "setting path: " path " to: " v)
                                  (set-edit-value id [path v]))}]

      [:div.bg-red-500
       "loading" (str id)
       [debug-ui state-a options-a]])
    (finally
      (println "clj-option-ui cleanup")
      (dispose!))))