(ns football.teams
  (:require [yesql.core :as yes]
            [clojure.data.json :as json]
            [football.core :refer [db]]))

(yes/defqueries "sql/teams.sql" {:connection db})

(defn add-player [player]
  (insert-player<! {:name (:name player)
                    :position (:position player)
                    :skills (json/write-str (:skills player))}))
