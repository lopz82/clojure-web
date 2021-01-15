(ns cljs-web.subs
  (:require
    [re-frame.core :as re-frame]
    [clojure.string :as str]
    [cljs-web.calc :as calc]))

(re-frame/reg-sub
  :get-participants
  (fn [db _]
    (:participants db)))

(re-frame/reg-sub
  :num-fields
  :<- [:get-participants]
  (fn [participants _]
    (max 2
         (->> participants
              (filter #(not (str/blank? %)))
              (count)
              (inc)))))

(re-frame/reg-sub
  :get-pairs
  :<- [:get-participants]
  (fn [participants _]
    (calc/assign participants)))
