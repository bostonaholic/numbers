(ns numbers.metrics-test
  (:require [clojure.test :refer :all]
            [numbers.metrics :refer :all]))

(deftest accuracy-test
  (testing "all values are 0 initially"
    (is (= {:total 0 :correct 0 :percentage 0.0}
           (accuracy '() '()))))

  (testing "100% correct when the closest neighbor is a match"
    (let [validations (list {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)})
          trainers (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                         {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})]
      (is (= {:total 1 :correct 1 :percentage 1.0}
             (accuracy validations trainers)))))

  (testing "0% correct when the closest neighbor is not a match"
    (let [validations (list {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)})
          trainers (list {:label 4 :pixels (list 2 9 0 0 0 1 9 9 0)}
                         {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})]
      (is (= {:total 1 :correct 0 :percentage 0.0}
             (accuracy validations trainers)))))

  (testing "50% correctness with 2 total and 1 correct"
    (let [validations (list {:label 7 :pixels (list 0 0 0 9 0 9 0 9 9)}
                            {:label 1 :pixels (list 6 1 6 6 0 6 6 0 6)})
          trainers (list {:label 1 :pixels (list 9 0 9 9 0 9 9 0 9)}
                         {:label 4 :pixels (list 0 9 0 0 0 0 9 9 0)})]
      (is (= {:total 2 :correct 1 :percentage 0.5}
             (accuracy validations trainers))))))
