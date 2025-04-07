(ns options.flowy.core
  (:require
   [missionary.core :as m]
   [options.core :refer [apply-option ui-state]])
  (:import [missionary Cancelled]))

(defonce running-a (atom {}))

(defn create! [id initial-state options]
  (let [state (atom initial-state)
        flow (m/signal (m/watch state))
        data {:id id
              :options options
              :state state
              :flow flow}]
    (swap! running-a assoc id data)
    data))

(defn destroy! [id]
  (swap! running-a dissoc id))

(defn mix
  "Return a flow which is mixed by flows"
  ; will generate (count flows) processes, 
  ; so each mixed flow has its own process
  [& flows]
  (m/ap (m/?> (m/?> (count flows) (m/seed flows)))))

(defn get-edit-flow [id]
  (when-let [{:keys [options flow]} (get @running-a id)]
    (let [latest-state-flow  (m/eduction
                              (map (fn [state]
                                     (println "state (of edit-flow): " state)
                                     {:state (ui-state state options)}))
                              (m/latest identity flow))
          option-flow (m/seed [{:options options}])]
      (mix option-flow latest-state-flow)
      #_(m/signal
         (m/ap
          (m/amb
           {:options options}
           (loop [state (m/?> last-state-flow)]
             (m/amb {:state (ui-state state options)}
                    (recur (m/?> last-state-flow))))))))))

(defn set-edit-value [id path-value-vec]
  (when-let [{:keys [state]} (get @running-a id)]
    (swap! state apply-option path-value-vec)))

(defn create-option-flow [id initial-state options]
  (m/ap
   (let [{:keys [flow]} (create! id initial-state options)]
     (try

       (catch Cancelled _
         (println "shutting down..")
         (destroy! id)
                ;(m/? shutdown!)
         true)))))

