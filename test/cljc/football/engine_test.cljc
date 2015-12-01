(ns football.engine-test
  (:require [football.engine :as engine]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])))


(def player1 {:name "P1"
               :skills {:control 3
                        :speed 3
                        :tackle 4
                        :dribbling 10
                        :shoot 4}
               :position :attack})

(def player2  {:name "P2"
               :skills {:control 3
                        :speed 3
                        :tackle 4
                        :dribbling 10
                        :shoot 4}
               :position :attack})

;; TODO: add tests using test.check since mos tof the stuff can be purely tested

(defn- random-skills []
  (apply merge
         (for [skill engine/skills] {skill (rand-int engine/RANKING-RANGE)})))

(defn- random-players [n]
  (for [idx (range n)]
    (engine/make-player (str "Player" n) (random-skills) (rand-nth engine/positions))))

(t/deftest make-player-test
  (t/testing "Invalid arguments given in"
    (t/is (thrown? RuntimeException
                   (engine/make-player "name" {:control 10} :attack))))

  (t/testing "Valid player generation"
    (let [player (engine/make-player "name" (random-skills) :defense)]
      (t/is (= (:position player) :defense)))))

(t/deftest team-selection-test
  ;;TODO: check that passing an odd number gives an error
  (t/testing "Simple selection"
    (let [players [(engine/make-player "p1" (random-skills) :attack)
                   (engine/make-player "p2" (random-skills) :defense)]
          teams (engine/make-teams players)]
      (t/is (every? #(= 1 (count %)) teams)))))


(t/deftest players-ordering-test
  (let [p1 (engine/make-player "p1" {:control 3
                                     :speed 3
                                     :dribbling 3
                                     :shoot 3
                                     :tackle 3}
                               :attack)
        p2 (engine/make-player "p2" {:control 3
                                     :speed 4
                                     :dribbling 3
                                     :shoot 4
                                     :tackle 4}
                               :attack)]

    (t/is (= (engine/order-players [p1 p2]) [p2 p1]))))

(t/deftest pick-intersperse-test
  (let [v1 '(1 2 3 4)]
    (t/is (= (engine/pick-one-one v1) '(1 3 2 4)))))

(t/deftest list-all-teams-combo-test
  (t/testing "4 players"
    (t/is (= (count (engine/list-teams (range 4))) 3))))

(t/deftest rank-team-test
  (t/testing "Not balanced team selection"
    (let [team1 [player1]
          team2 [player2]]
      (t/is (= 0 (engine/rank-selection [team1 team2]))))))

(t/deftest brute-force-selection-test
  (t/testing "Two players only"
    (let [players [player1 player2]]
      (t/is (= (count (engine/brute-force-selection players)) 1)))))

(t/deftest rankings-from-names-test
  (t/testing "Simple conversion"
    (t/is (= (engine/rankings-from-names ["P1"] [player1 player2]) [player1]))))
