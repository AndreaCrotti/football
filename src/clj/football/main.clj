(ns football.main
  (:gen-class
   :main true)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [clojure.java.io :as io]
            [football.engine :as engine]))

;; TODO: simple get an EDN file with some rankings and spit out
;; some teams

(def cli-options
  [["-c" "--config" "File containing all the rankings"]])
    ;#_:parse-fn #(.exists (io/as-file %))]])

(defn team-names [team]
  (map :name team))

(defn -main [& args]
  (clojure.pprint/pprint args)
  (let [options (parse-opts args cli-options)
        players (load-file (-> options :arguments (first))) ; it really behave better
        teams (engine/make-teams players)]

    (clojure.pprint/pprint players)
    (print "Team number one = " (team-names (first teams)))
    (print "Team number two = " (team-names (second teams)))))

