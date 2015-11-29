(ns football.core
  (:require [clojure.java.jdbc :as jdbc]
            [yesql.core :as yes]
            [environ.core :refer [env]]))


(def db {:classname "com.postgresql.jdbc.Driver"
         :subprotocol "postgresql"
         :subname (str "//localhost:5432/" (env :POSTGRES_DATABASE))
         :user (env :POSTGRES_USER)
         :password (env :POSTGRES_PASSWORD)})


(yes/defqueries "sql/create.sql" {:connection db})
(yes/defqueries "sql/queries.sql" {:connection db})

(defn init-database []
  (create-games-table)
  (create-players-table)
  (create-skills-table)
  (create-teams-table))
