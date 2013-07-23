(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest sort-by-score-test
  (testing "returns a sorted collection of maps by the score key"
    (is (= [{:score 2} {:score 3} {:score 5}]
           (sort-by-score [{:score 3} {:score 2} {:score 5}])))))

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
    (is (= {:score Integer/MAX_VALUE :match {}}
           (nearest-neighbor '() '()))))

  (testing "returns the nearest neighbor for an exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 9 0 9 9 0 9 9 0 9)]
      (is (= {:score 0 :match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (nearest-neighbor unknown knowns)))))

  (testing "returns the nearest neighbor for a non-exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:score 55 :match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (nearest-neighbor unknown knowns))))))
