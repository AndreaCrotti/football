(ns football.core
  (:require [clojure.java.jdbc :as jdbc]
            [yesql.core :as yes]
            [environ.core :refer [env]]
            [ragtime.repl :as repl]
            [ragtime.jdbc :refer [sql-database load-resources]]))


(def db {:classname "com.postgresql.jdbc.Driver"
         :subprotocol "postgresql"
         :subname (str "//localhost:5432/" (env :postgres-database))
         :user (env :postgres-user)
         :password (env :postgres-password)})


(yes/defqueries "sql/queries.sql" {:connection db})

(def postgres-uri-prod
  (format "jdbc:postgresql://%s:%d/%s?user=%s&password=%s"
          (env :postgres-host)
          (env :postgres-port)
          (env :postgres-database)
          (env :postgres-user)
          (env :postgres-password)))

(defn load-config []
  {:datastore (sql-database {:connection-uri postgres-uri})
   :migrations (load-resources "migrations")})

(defn migrate []
  (repl/migrate (load-config)))

(defn rollback []
  (repl/rollback (load-config)))
