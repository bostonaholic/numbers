(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest closest-neighbor-test
  (testing "empty knowns"
    (is (= {:best-score Integer/MAX_VALUE :best-match {}}
           (closest-neighbor '() '()))))

  (testing "returns the closest neighbor for an exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 9 0 9 9 0 9 9 0 9)]
      (is (= {:best-score 0 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (closest-neighbor unknown knowns)))))

  (testing "returns the closest neighbor for a non-exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:best-score 55 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (closest-neighbor unknown knowns))))))

(deftest accuracy-test
  (testing "all values are 0 initially"
    (is (= {:total 0 :correct 0 :percentage 0.0}
           (accuracy '() '()))))

  (testing "100% correct when the closest neighbor is a match"
    (let [validations (list {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)})
          trainers (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                         {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})]
      (is (= {:total 1 :correct 1 :percentage 1}
             (accuracy validations trainers)))))

  (testing "0% correct when the closest neighbor is not a match"
    (let [validations (list {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)})
          trainers (list {:label 4 :pixels (list 2 9 0 0 0 1 9 9 0)}
                         {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})]
      (is (= {:total 1 :correct 0 :percentage 0}
             (accuracy validations trainers))))))
