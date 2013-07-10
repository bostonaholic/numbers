(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest split-to-integers-test
  (testing "spliting comma separated strings into integers"
    (is (= '(1 2 3) (split-to-integers "1,2,3")))))

(deftest read-file-test)

(deftest parse-file-test)

(deftest square-test
  (testing "squaring a number"
    (is (= 1 (square 1)))
    (is (= 16 (square 4)))))

(deftest cubed-test)

(deftest square-of-difference-test
  (testing "squares the difference of two numbers"
    (is (= 16 (square-of-difference 9 5)))))

(deftest euclidean-distance-test)

(deftest closest-neighbor-test)
