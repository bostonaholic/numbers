;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.parser :refer [parse-file]]))

(def validation-set (parse-file "validationset.csv")) ;; 500 examples
(def training-set (parse-file "trainingset.csv")) ;; 5,000 examples

(defn square [^Integer n] (* n n))
(defn cubed [^Integer n] (* n n n))
(defn square-of-difference [x y] (square (- x y)))

(defn square-of-differences [x y]
  (map square-of-difference x y))

(defn euclidean-distance
  "calculates the Euclidean Distance between two vectors
   DIST = (X1-Y1)^2 + (X2-Y2)^2 + ... + (Xn-Yn)^2"
  [x y]
  (reduce + (square-of-differences x y)))

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbor
  "classifier"
  ([knowns unknown]
     (closest-neighbor knowns unknown Integer/MAX_VALUE {}))
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
  (let [examples validation-set]
    (map (partial closest-neighbor examples) (take 3 (parse-file filename)))))

(comment
  (closest-neighbor examples (first training-set))
  (map (partial closest-neighbor validation-set) (take 1000 training-set))
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
