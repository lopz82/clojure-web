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
  (fn [db [_ id val]]
    (update-in db [:participants] assoc id (clojure.string/trim val))))

(re-frame/reg-event-db
  :upsert-email
  (fn [db [_ id val]]
    (update-in db [:emails] assoc id (clojure.string/trim val))))

(re-frame/reg-event-db
  :calculate-pairs
  (fn [db _]
    (->> (:participants db)
         (cljs-web.calc/assign)
         (update-in db [:pairs] merge))))
