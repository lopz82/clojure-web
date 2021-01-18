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
              :id          (str "participant-" (inc id))
              :style       {:border "1px solid #CCC"}
              :placeholder (str "Participant " (inc id))
              :on-change   emit}]]))

(defn email
  [id]
  (let [get-value (fn [e] (-> e .-target .-value))
        emit (fn [e] (re-frame/dispatch [:upsert-email id (get-value e)]))]
    [:div
     [:input {:type        "email"
              :required    true
              :id          (str "email-" (inc id))
              :size        30
              :style       {:border (str "1px solid #CCC")}
              :placeholder (str "Email " (inc id))
              :on-change   emit}]]))

(defn fields-participants
  []
  (let [participants @(re-frame/subscribe [:num-fields])]
    [:div
     (for [id (range participants)]
       ^{:key id} [:div [field id] [email id]])]))

(defn send-button
  []
  [:button {:type "button"
            :on-click #(re-frame/dispatch [:calculate-pairs])} "Send"])

(defn participants-list
  []
  [:ul
   (for [n @(re-frame/subscribe [:get-participants])]
     [:li n])])

(defn pairs-list
  []
  [:ul
   (for [[x y] @(re-frame/subscribe [:get-pairs])]
     [:li (str x " --> " y)])])

(defn main-panel []
  [:div
   (str @(re-frame/subscribe [:get-participants]))
   (str @(re-frame/subscribe [:get-emails]))
   (str @(re-frame/subscribe [:get-pairs]))
   [fields-participants]
   [send-button]
   [participants-list]
   [pairs-list]])

