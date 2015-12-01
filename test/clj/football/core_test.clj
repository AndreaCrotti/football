(ns football.core-test
  (:require [clojure.set :as set]
            [clojure.test :as t]
            [clojure.data.json :as json]
            [football.core :as sut]
            [football.players :as pl]
            [football.teams :as th]))

(defn football-db [f]
  (sut/migrate)
  (f)
  (sut/rollback))

(t/use-fixtures :each football-db)

(def sample-skills {:shoot 1 :speed 2 :dribbling 3 :control 4})

(defn- random-player []
  {:name (format "Player%d" (rand-int 10))
   :position (rand-nth ["attack" "defense" "middle"])
   :skills (json/write-str sample-skills)})

(defn- teams [players]
  (json/write-str (for [pl players]
                    {:id (:id pl)})))

(t/deftest test-using-db
  (t/testing "Insert and get players"
    (pl/insert-player<! (random-player))

    (let [res (pl/fetch-players)]
      (t/is (= (count res) 1))))

  (t/testing "Create a new team with two players"
    (let [p1 (pl/insert-player<! (random-player))
          p2 (pl/insert-player<! (random-player))
          team (th/insert-team<! {:players (teams [p1 p2])})
          all-teams (th/fetch-teams)]
      (t/is (= (count all-teams) 1)))))
