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

(defn order-players [players]
  (->> players
       (sort-by #(apply + (vals (:skills %))))
       (reverse)))

(defn make-teams [players]
  "Create the two opposing teams"
  (when (even? (count players))
    (let [middle (/ (count players) 2)]
      (list (take middle players) (drop middle players)))))

;;TODO: first simple implementation is the greedy choice of best players
