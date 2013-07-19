(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest closest-neighbor-test
  (testing "empty knowns"
    (is (= {:best-score Integer/MAX_VALUE :best-match {}} (closest-neighbor '() {}))))

  (testing "returns the closest neighbor for an exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}]
      (is (= {:best-score 0 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}} (closest-neighbor knowns unknown)))))

  (testing "returns the closest neighbor for a non-exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)}]
      (is (= {:best-score 55 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}} (closest-neighbor knowns unknown))))))
