(ns numbers.core-test
  (:require [clojure.test :refer :all]
            [numbers.core :refer :all]))

(deftest split-to-integers-test
  (testing "spliting comma separated strings into integers"
    (is (= '(1 2 3) (split-to-integers "1,2,3")))))

(deftest read-file-test
  (testing "able to read a file from disk"
    (let [file (read-file "test/testfile.csv")]
      (is (not (empty? file))))))

(deftest parse-file-test
  (testing "parse a file into the integer format"
    (let [parsed-file (parse-file "test/testfile.csv")]
      (is (= 1 (count parsed-file)))
      (is (= '(1 2 3 4 5 6 7 8 9 10) (first parsed-file))))))

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

(deftest euclidean-distance-test
  (testing "calculates the euclidean distance between two vectors"
    (is (= 0 (euclidean-distance [0 0] [0 0])))
    (is (= 13 (euclidean-distance [4 2] [6 5])))))

(deftest closest-neighbor-test)
