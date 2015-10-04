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
  [["-c" "--config" "File containing all the rankings"]

   ["-s" "--strategy" "Use greedy or brute force approach"
    :parse-fn keyword
    :validate [#(contains? strategies (keyword %)) "Invalid strategy passed in"]]])
    
(defn team-names [team]
  (map :name team))

(defn selection-repr [sel]
  (str "- team1:  " (clojure.string/join ", " (team-names (first (:selection sel))))
       "\n-team2: " (clojure.string/join ", " (team-names (second (:selection sel))))
       "\n-difference = " (:ranking sel)))

(defn -main [& args]
  (let [options (parse-opts args cli-options)
        players (load-file (-> options :arguments (first))) ; it really behave better
        selections (engine/brute-force-selection players 3)]
    ;; use this information 
    ;; teams (engine/make-teams players)
    
    (doseq [sel selections]
      ;; (clojure.pprint/pprint sel)
      (println (selection-repr sel)))))
