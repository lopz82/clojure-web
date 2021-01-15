(ns cljs-web.events
  (:require
    [re-frame.core :as re-frame]
    [cljs-web.db :as db]))


(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  :timer
  (fn [db [_ new-time]]
    (assoc db :time new-time)))

(re-frame/reg-event-db
  :initialize
  (fn [_ _]
    {:time          (js/Date.)
     :time-color    "red"
     :button-clicks 0
     :participants  2}))

(re-frame/reg-event-db
  :time-color-change
  (fn [db [_ new-color-value]]
    (assoc db :time-color new-color-value)))

(re-frame/reg-event-db
  :click-button
  (fn [db _]
    (assoc db :button-clicks (inc (:button-clicks db)))))

(re-frame/reg-event-db
  :add-participant
  (fn [db [_ new-participant]]
    (update-in db :participants conj new-participant)))

(re-frame/reg-event-db
  :upsert-participant
  [re-frame/debug]
  (fn [db [_ id val]]
    (update-in db [:participants] assoc (keyword (str id)) val)))
