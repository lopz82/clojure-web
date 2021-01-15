(ns cljs-web.calc
  (:require [clojure.set :as set]))

(defn valid-assignments [user choices assignments]
  (seq (set/difference (set/union (set choices)
                                  (set (keys assignments)))
                       (set [user])
                       (set (vals assignments)))))

(defn random-assignment [choices assignments]
  (->> (valid-assignments (first choices)
                          choices
                          assignments)
       (rand-nth)
       (assoc assignments (first choices))))

(defn assign
  ([choices]
   (assign choices {}))
  ([choices assignments]
   (if (empty? choices)
     assignments
     (recur (rest choices) (random-assignment choices assignments)))))
