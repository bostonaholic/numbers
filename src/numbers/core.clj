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
  (line-seq (reader (resource filename))))

(defn parse-file
  ""
  [filename]
  (map split-to-integers (rest (read-file filename))))

(defonce examples
  (let [all (first (parse-file "validationset.csv"))]
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
                       (dist (:pixels (first knowns)) (:pixels unknown))
                       (first knowns)))
  ([knowns unknown best-score best-match]
     (if (seq knowns)
       (let [score (dist (:pixels (first knowns)) (:pixels unknown))]
         (if (< score best-score)
           (recur (rest knowns) unknown score (first knowns))
           (recur (rest knowns) unknown best-score best-match)))
       {:best-score best-score
        :best-match best-match})))

(defn train
  ""
  [filename]
  (map (partial closest-neighbor examples) (take 3 (parse-file filename))))

(comment
  (closest-neighbor examples (first (parse-file "trainingset.csv")))
  (map (partial closest-neighbor examples) (take 100 (parse-file filename)))
  )

(defn -main [& args]
  (train "trainingset.csv"))

(defn accuracy
  "calculates accuracy metrics for the closest-neighbor function"
  []
  {:total 0
   :correct 0})

(defn performance
  "calculates performance metrics for the closest-neighbor function"
  []
  {:time (time (closest-neighbor [] []))})
