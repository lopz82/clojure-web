(ns cljs-web.views
  (:require
    [re-frame.core :as re-frame]
    [cljs-web.subs :as subs]))

(defn clock
  []
  (let [colour @(re-frame/subscribe [:time-color])
        time (-> @(re-frame/subscribe [:time])
                 .toTimeString
                 (clojure.string/split " ")
                 first)]
    [:div.example-clock {:style {:color colour}} time]))

(defn color-input
  []
  (let [gettext (fn [e] (-> e .-target .-value))
        emit (fn [e] (re-frame/dispatch [:time-color-change (gettext e)]))]
    [:div.color-input
     "Display color: "
     [:input {:type      "text"
              :style     {:border "1px solid #CCC"}
              :value     @(re-frame/subscribe [:time-color])
              :on-change emit}]]))

(defn button-counter
  []
  [:input {:value    (str @(re-frame/subscribe [:button-clicks]) " clicks!")
           :type     "button"
           :on-click #(re-frame/dispatch [:click-button])}])

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
     (for [id (range participants)]
       ^{:key id} [field id])]))

(defn participants-list
  []
  [:ul
   (for [n (vals @(re-frame/subscribe [:get-participants]))]
     [:li n])])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [:h1 "Current time:"]
     [clock]
     [color-input]
     [button-counter]
     [fields-participants]
     [participants-list]]))

