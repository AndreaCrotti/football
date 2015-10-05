(ns football.main
  (:gen-class
   :main true)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]
            [football.engine :as engine]))

(def strategies [:brute-force :greedy])

;; TODO: simple get an EDN file with some rankings and spit out
;; some teams

(def cli-options
  [["-r" "--rankings RANKINGS" "File containing all the rankings"]

   ["-l" "--player-list PLAYERS_LIST" "File containing the players playing"]

   ["-s" "--strategy STRATEGY" "Use greedy or brute force approach"
    :parse-fn keyword
    :default :greedy
    :required true
    :validate [#(contains? strategies (keyword %)) "Invalid strategy passed in"]]])
    
(defn team-names [team]
  (map :name team))

(defn selection-repr [sel]
  (str "- team1:  " (clojure.string/join ", " (team-names (first (:selection sel))))
       "\n-team2: " (clojure.string/join ", " (team-names (second (:selection sel))))
       "\n-difference = " (:ranking sel)))

(defn -main [& args]
  (let [options (parse-opts args cli-options)
        rankings(load-file (-> options :options :rankings)) ; it really behave better
        players (load-file (-> options :options :player-list))
        players-rankings (filter #(contains? (set players) (:name %)) rankings)
        strategy (-> options :options :strategy)
        selections (cond
                     (= strategy :greedy) [(engine/make-teams players-rankings)]
                     (= strategy :brute-force) (engine/brute-force-selection players-rankings 3))]
    
    (clojure.pprint/pprint selections)
    (doseq [sel selections]
      (println "------------")
      ;; (clojure.pprint/pprint sel)
      (println (selection-repr sel))
      (println "------------"))))
