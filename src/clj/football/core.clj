(ns football.core
  (:require [clojure.java.jdbc :as jdbc]
            [yesql.core :as yes]
            [environ.core :refer [env]]
            [ragtime.repl :as repl]
            [clj-postgresql.core :as pg]
            [ragtime.jdbc :refer [sql-database load-resources]]))


(def db
  (pg/pool :host "localhost" :user "football" :dbname "football" :password "football"))

;; TODO: should use the env variables instead 
(def postgres-uri
  "jdbc:postgresql://localhost:5432/football?user=football&password=football")

(defn load-config []
  {:datastore (sql-database {:connection-uri postgres-uri})
   :migrations (load-resources "migrations")})

(defn migrate []
  (repl/migrate (load-config)))

(defn rollback []
  (repl/rollback (load-config)))
