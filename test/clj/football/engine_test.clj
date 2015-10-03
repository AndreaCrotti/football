(ns football.engine-test
  (:require [football.engine :as engine]
            [clojure.test :as t]))

;; TODO: use recursion just for fun not because is actually useful
(defn- random-skills []
  (apply merge (for [skill engine/skills] {skill (rand-int engine/RANKING-RANGE)})))

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
