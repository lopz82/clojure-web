(ns cljs-web.subs
  (:require
    [re-frame.core :as re-frame]
    [clojure.string :as str]))

(re-frame/reg-sub
  :get-participants
  (fn [db _]
    (:participants db)))

(re-frame/reg-sub
  :num-fields
  (fn [_ _]
    (re-frame/subscribe [:get-participants]))
  (fn [participants _]
    (max 2
         (->> participants
              (filter #(not (str/blank? (second %))))
              (count)
              (inc)))))
