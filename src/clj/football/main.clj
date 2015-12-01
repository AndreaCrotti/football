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

   ["-n" "--selection-number SELECTION_NUMBER" "Number of best selections to show"
    :parse-fn #(. Integer parseInt %)
    :default 3]

   ["-s" "--strategy STRATEGY" "Use greedy or brute force approach"
    :parse-fn keyword
    :default :greedy
    :required true
    :validate [#(contains? strategies (keyword %)) "Invalid strategy passed in"]]])
    
(defn team-names [team]
  (sort (map :name team)))

(defn selection-repr [sel]
  (str "- team1:  " (clojure.string/join ", " (team-names (first (:selection sel))))
       "\n- team2: " (clojure.string/join ", " (team-names (second (:selection sel))))))

(defn -main [& args]
  (let [options (:options (parse-opts args cli-options))
        rankings(load-file (:rankings options)) ; it really behave better
        players (load-file (:player-list options))
        players-rankings (filter #(contains? (set players) (:name %)) rankings)
        selections (engine/brute-force-selection players-rankings)]
    ;; use this information 
    ;; teams (engine/make-teams players)
    
    (doseq [sel (take (:selection-number options) selections)]
      (println "------------")
      ;; (clojure.pprint/pprint sel)
      (println (selection-repr sel))
      (println "------------"))))
