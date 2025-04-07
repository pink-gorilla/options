(ns demo.app
  (:require
   [frontend.css :refer [css-loader]]
   [frontend.notification :refer [notification-container]]
   [frontend.dialog :refer [modal-container]]
   [webly.spa.env :refer [get-resource-path]]))

(defn link [url text]
   [:a {:href url} [:span {:style {:padding "2px"}} text]])

(defn nav []
  [:div.bg-blue-300
   [link "/#" "goto options"]
   [link "/#/controls" "goto controls"]
   [link "/#/quanta" "goto quanta"]
   [link "/#/clj" "goto clj-options"]
   ])

(defn wrap-app [page match]
  [:div
   [modal-container]
   [notification-container]
   [css-loader (get-resource-path)]
   [nav]
   [page match]])

(def routes
  [["/" {:name 'demo.page.core/page}]
   ["/controls" {:name 'demo.page.controls/page}]
   ["/quanta" {:name 'demo.page.quanta/page}]
   ["/clj" {:name 'demo.page.clj/page}]
   ])

  