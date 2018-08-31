(ns cljs-time.core
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :as rf]
            [clojure.string :as str]))

(enable-console-print!)

(defn dispatch-timer-event []
  (rf/dispatch [:timer (js/Date.)]))

(defonce do-timer (js/setInterval dispatch-timer-event 1000))

(rf/reg-event-db
 :initialize
 (fn [_ _] {:time (js/Date.)
            :time-color "#f88"}))

(rf/reg-event-db
 :change-color
 (fn [db [_ new-color]]
   (assoc db :time-color new-color)))

(rf/reg-event-db
 :timer
 (fn [db [_ new-time]]
   (assoc db :time new-time)))

(rf/reg-sub
 :time
 (fn [db _]
   (:time db)))

(rf/reg-sub
 :time-color
 (fn [db _]
   (:time-color db)))

(defn clock []
  )



(defonce app-state (atom {:text "Hello worsld!"}))

(defn hello-world []
  [:div
   [:h3 "Ed123it this and watch it change!"]])

;; (reagent/render-component [hello-world]
;;                           (. js/document (getElementById "app")))

(defn ^:export run
  []
  (rf/dispatch-sync [:initialize])
  (reagent/render-component [hello-world]
                           (. js/document (getElementById "app"))))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
