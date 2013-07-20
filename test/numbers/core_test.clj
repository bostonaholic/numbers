(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest k-nearest-neighbor-test
  (testing "getting the first nearest neighbor"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:best-score 55 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (k-nearest-neighbor 1 unknown knowns)))))

  (testing "getting the first two nearest neighbors"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= clojure.lang.LazySeq (class (k-nearest-neighbor 2 unknown knowns)))))))

(deftest nearest-neighbor-test
  (testing "empty knowns"
    (is (= {:best-score Integer/MAX_VALUE :best-match {}}
           (nearest-neighbor '() '()))))

  (testing "returns the nearest neighbor for an exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 9 0 9 9 0 9 9 0 9)]
      (is (= {:best-score 0 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (nearest-neighbor unknown knowns)))))

  (testing "returns the nearest neighbor for a non-exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:best-score 55 :best-match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (nearest-neighbor unknown knowns))))))
