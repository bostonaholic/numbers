(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest square-test
  (testing "squaring a number"
    (is (= 1 (square 1)))
    (is (= 16 (square 4)))))

(deftest cubed-test
  (testing "cubing a number"
    (is (= 1 (cubed 1)))
    (is (= 27 (cubed 3)))))

(deftest square-of-difference-test
  (testing "squares the difference of two numbers"
    (is (= 16 (square-of-difference 9 5)))))

(deftest square-of-differences-test
  (testing "returns the square of differences for two lists"
    (is (= '(16) (square-of-differences '(9) '(5))))
    (is (= '(16 9 0) (square-of-differences '(9 6 7) '(5 3 7))))))

(deftest euclidean-distance-test
  (testing "calculates the euclidean distance between two vectors"
    (is (= 0 (euclidean-distance [0 0] [0 0])))
    (is (= 13 (euclidean-distance [4 2] [6 5])))))
