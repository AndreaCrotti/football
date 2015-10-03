(ns football.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [football.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'football.core-test))
    0
    1))
