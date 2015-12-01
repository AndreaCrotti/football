(ns football.players
  (:require [yesql.core :as yes]
            [clojure.data.json :as json]
            [football.core :refer [db]]))

(yes/defqueries "sql/teams.sql" {:connection db})
