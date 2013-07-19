;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.parser :refer [parse-file]]
   [numbers.maths :refer [euclidean-distance]]))

(def validation-set (parse-file "validationset.csv")) ;; 500 examples
(def training-set (parse-file "trainingset.csv")) ;; 5,000 examples

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbor
  "classifier"
  ([knowns unknown]
     (closest-neighbor knowns unknown Integer/MAX_VALUE {}))
  ([knowns unknown best-score best-match]
     (if (and (seq knowns) (not (= 0 best-score)))
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

(defn accuracy
  "calculates accuracy metrics for the closest-neighbor function"
  []
  {:total 0
   :correct 0})

(defn performance
  "calculates performance metrics for the closest-neighbor function"
  []
  {:time (time (closest-neighbor [] []))})
