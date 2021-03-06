(ns numbers.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [numbers.core :refer :all]))

(deftest sort-by-score-test
  (testing "returns a sorted collection of maps by the score key"
    (is (= [{:score 2} {:score 3} {:score 5}]
           (sort-by-score [{:score 3} {:score 2} {:score 5}])))))

(deftest k-nearest-neighbors-test
  (testing "getting the first nearest neighbor"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:score 55 :match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (k-nearest-neighbors 1 unknown knowns)))))

  (testing "getting the first two nearest neighbors"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)}
                       {:label 7 :pixels (list 0 0 0 9 0 9 0 9 9)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= clojure.lang.LazySeq (class (k-nearest-neighbors 2 unknown knowns)))))))

(deftest naive-nearest-neighbor-test
  (testing "empty knowns"
    (is (= {:score Integer/MAX_VALUE :match {}}
           (naive-nearest-neighbor '() '()))))

  (testing "returns the nearest neighbor for an exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 9 0 9 9 0 9 9 0 9)]
      (is (= {:score 0 :match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (naive-nearest-neighbor unknown knowns)))))

  (testing "returns the nearest neighbor for a non-exact match"
    (let [knowns (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                       {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})
          unknown (list 6 1 6 6 0 6 6 0 6)]
      (is (= {:score 55 :match {:label 1 :pixels '(9 0 9 9 0 9 9 0 9)}}
             (naive-nearest-neighbor unknown knowns))))))
