(ns football.engine)

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

(defn make-player [name skills position]
  ;;TODO: how do I enforce position to be always something from
  ;;a given list?
  {:name name
   :skills skills
   :position position})


(defn pick-one-one [xs]
  "Interleave a list picking one from each"
  (let [size (count xs)
        middle (/ size 2)
        evens (for [idx (range middle)] (nth xs (* idx 2)))
        odds (for [idx (range middle)] (nth xs (+ 1 (* idx 2))))]

    (concat evens odds)))

(defn order-players [players]
  "Order players putting the best players first"
  (->> players
       (sort-by #(apply + (vals (:skills %))))
       (reverse)))

(defn make-teams [players]
  "Create the two opposing teams by picking one at a time"
  (when (even? (count players))
    (let [ordered-players (order-players players)
          middle (/ (count players) 2)
          picked (pick-one-one ordered-players)]

      (list (take middle picked) (drop middle picked)))))

;;TODO: first simple implementation is the greedy choice of best players
