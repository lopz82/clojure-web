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
  (fn [_ _]))

(re-frame/reg-event-db
  :upsert-participant
  [re-frame/debug]
  (fn [db [_ id val]]
    (update-in db [:participants] assoc id val)))
