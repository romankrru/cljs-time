(ns cljs-time.core
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :as rf]
            [clojure.string :as str]))

(defonce do-timer
  (js/setInterval(fn [] (rf/dispatch [:timer (js/Date.)]) ) 1000))

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
  [:div.example-clock
   {:style {:color @(rf/subscribe [:time-color])}}
   (-> @(rf/subscribe [:time])
       .toTimeString
       (str/split " ")
       first)])

(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @(rf/subscribe [:time-color])
            :on-change #(rf/dispatch [:change-color
                                      (-> % .-target .-value)])}]])

(defn ui []
  [:div
   [:h1 "Hello world, it is now"]
   [clock]
   [color-input]])

(defn ^:export run []
  (rf/dispatch-sync [:initialize])
  (reagent/render [ui]
                  (js/document.getElementById "app")))
