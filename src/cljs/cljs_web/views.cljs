(ns cljs-web.views
  (:require
    [re-frame.core :as re-frame]
    [cljs-web.subs :as subs]))

(defn field
  [id]
  (let [get-value (fn [e] (-> e .-target .-value))
        emit (fn [e] (re-frame/dispatch [:upsert-participant id (get-value e)]))]
    [:div
     [:input {:type        "text"
              :id          (str "participant-" id)
              :style       {:border "1px solid #CCC"}
              :placeholder (str "Participant " id)
              :on-change   emit}]
     (str "participant-" id)
     (str id "-" (keyword id) @(re-frame/subscribe [:get-participants]))]))

(defn fields-participants
  []
  (let [participants @(re-frame/subscribe [:num-fields])]
    [:div
     (for [id (range 1 (inc participants))]
       ^{:key id} [field id])]))

(defn participants-list
  []
  [:ul
   (for [n (vals @(re-frame/subscribe [:get-participants]))]
     [:li n])])

(defn pairs-list
  []
  [:ul
   (for [[x y] @(re-frame/subscribe [:get-pairs])]
     [:li (str x " --> " y)])])

(defn main-panel []
    [:div
     [fields-participants]
     [participants-list]
     [pairs-list]])
