(ns football.engine
  (:require [schema.core :as s]))

(def RANKING-RANGE 10)

;; TODO: use schemas and protoypes for the logic here

(def skills
  "Useful metrics needed to select a given player"
  [:control
   :speed
   :dribbling
   :shoot
   :tackle])

(def positions
  [:attack
   :defense
   :middle])

(def Skills
  "Schema for skills available"
  {:control s/Int
   :speed s/Int
   :dribbling s/Int
   :shoot s/Int
   :tackle s/Int})

(def Player
  {:name s/Str
   :skills Skills
   :position (apply s/enum positions)})


(defn make-player [name skills position]
  {:post [(s/validate Player %)]}
  "Create a player post validating on the right format"

  {:name name
   :skills skills
   :position position})

(defn pick-one-one [xs]
  "Interleave a list picking one from each"
  (let [size (count xs)
        middle (/ size 2)
        ;; there is probably an esier way to generate this
        evens (for [idx (range middle)] (nth xs (* idx 2)))
        odds (for [idx (range middle)] (nth xs (+ 1 (* idx 2))))]

    (concat evens odds)))

(defn rank-player [player]
  "Return a single number to rank a given player"
  (apply + (vals (:skills player))))

(defn order-players [players]
  "Order players putting the best players first"
  (->> players
       (sort-by rank-player)
       (reverse)))

(defn make-teams [players]
  "Create the two opposing teams by picking one at a time"
  (when (even? (count players))
    (let [ordered-players (order-players players)
          middle (/ (count players) 2)
          picked (pick-one-one ordered-players)]

      (list (take middle picked) (drop middle picked)))))

;;TODO: first simple implementation is the greedy choice of best players