;; TODO: map->pmap
(ns numbers.core
  (:require
   [clojure.string :as str]
   [clojure.java.io :refer [resource reader]]))

(defn split-to-integers
  "returns a list of integers from a comma separated line"
  [line]
  (map #(Integer/parseInt %) (str/split line #",")))

(defn read-file
  "returns a lazy-seq of a file in the format specified in the project spec"
  [filename]
  (let [rdr (reader (resource filename))
        file (rest (line-seq rdr))]
    (map split-to-integers file)))

(defonce examples
  (let [all (read-file "validationset.csv")]
    (map (fn [line]
           {:label (first line)
            :pixels (rest line)}) all)))

(defn square [^Integer n] (* n n))
(defn cubed [^Integer n] (* n n n))

(defn square-of-difference [x y] (square (- x y)))

(defn euclidean-distance
  "calculates the Euclidean Distance between two vectors"
  [x y]
  (reduce + (map square-of-difference x y)))

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbor
  ""
  ([knowns unknown]
     (closest-neighbor (rest knowns)
                       unknown
                       (dist (first knowns) unknown)
                       (first knowns)))
  ([knowns unknown best-score best-match]
     (if (seq knowns)
       (let [score (dist (first knowns) unknown)]
         (if (< score best-score)
           (recur (rest knowns) unknown score (first knowns))
           (recur (rest knowns) unknown best-score best-match)))
       {:best-score best-score
        :best-match best-match})))

(defn -main [& args]
  (map (partial closest-neighbor examples)))

(defn accuracy
  "calculates accuracy metrics for the closest-neighbor function"
  []
  {:total 0
   :correct 0})

(defn performance
  "calculates performance metrics for the closest-neighbor function"
  []
  (time (closest-neighbor [] [])))
