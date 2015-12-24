(ns football.engine
  (:require
   [clojure.set :as set]
   [schema.core :as s]                                                                                                                 [clojure.math.combinatorics :as combo]))

(def RANKING-RANGE 10)

;; TODO: use schemas and protoypes for the logic here

(def positions
  [:attack
   :defense
   :middle])

(s/defrecord Skill
    [control :- s/Int
     speed :- s/Int
     dribbling :- s/Int
     shoot :- s/Int
     tackle :- s/Int])

(s/defrecord Player
    [name :- s/Str
     skills :- Skill
     position :- (apply s/enum positions)])


(defn make-skill [skills]
  (map->Skill skills))

(defn make-player [name skills position]
  {:post [(s/validate Player %)]}
;  "Create a player post validating on the right format"
  (map->Player
   {:name name
    :skills skills
    :position position}))

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

(defn list-teams [players]
  "List all the possible team combinations"
  (let [players-count (count players)
        size (/ (combo/count-combinations players 2) 2)
        team-size (/ (count players) 2)]

    (for [team1 (take size (combo/combinations players team-size))]
      (list team1 (into () (set/difference (set players) team1))))))

(defn rank-team [team]
  "Rank a single team simply by adding up all the scores from all the players"
  (apply + (map rank-player team)))

(defn rank-selection [teams]
  "Given a team return how balanced it is by looking at the scores"
  (Math/abs (- (rank-team (first teams))
               (rank-team (second teams)))))

(defn rankings-from-names [names rankings]
  (filter #(contains? (set names) (:name %)) rankings))

(defn rankings-to-names [rankings]
  (map :name rankings))

(defn names-as-keys [rankings]
  "Convert to a Map keying on the name of the player"
  (apply merge
         (for [rank rankings]
           ({:name rank} rank))))

(defn brute-force-selection [players]
  "Return the first number of best teams by using brute force"
  (let [all-selections (list-teams players)
        augmented (for [sel all-selections]
                    {:selection sel :ranking (rank-selection sel)})]
    (#(sort-by :ranking %) augmented)))
