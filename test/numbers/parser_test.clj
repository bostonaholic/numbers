(ns numbers.parser-test
  (:require [clojure.test :refer [deftest testing is]]
            [numbers.parser :refer :all]))

(deftest split-to-integers-test
  (testing "spliting comma separated strings into integers"
    (is (= '(1 2 3) (split-to-integers "1,2,3")))))

(deftest read-file-test
  (testing "able to read a file from disk"
    (let [file (read-file "test/read-file-test.csv")]
      (is (not (empty? file))))))

(deftest parse-line-test
  (testing "parse a line into the map we expect"
    (let [item (parse-line '(1 2 3 4 5 6 7 8 9 10))]
      (is (= 1 (:label item)))
      (is (= '(2 3 4 5 6 7 8 9 10) (:pixels item))))))

(deftest parse-file-test
  (testing "parse a file into the integer format"
    (let [parsed-file (parse-file "test/parse-file-test.csv")]
      (is (= 1 (count parsed-file))))))
