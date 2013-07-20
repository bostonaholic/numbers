;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.parser :refer [parse-file]]
   [numbers.maths :refer [euclidean-distance]]))

(def validation-set (parse-file "validationset.csv")) ;; 500 examples
(def training-set (parse-file "trainingset.csv")) ;; 5,000 examples

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbor
  "classifier (first (neighbors unknowns known))"
  ([unknown knowns]
     (closest-neighbor unknown knowns Integer/MAX_VALUE {}))
  ([unknown knowns best-score best-match]
     (if (and (seq knowns) (not (zero? best-score)))
       (let [score (dist (:pixels (first knowns)) unknown)]
         (if (< score best-score)
           (recur unknown (rest knowns) score (first knowns))
           (recur unknown (rest knowns) best-score best-match)))
       {:best-score best-score
        :best-match best-match})))

(defn correct?
  "returns if the closest neighbor found is the correct label"
  [test found]
  (and (= (:label test) (:label (:best-match found)))
       (not (nil? (:label (:best-match found))))))

(defn accuracy
  "calculates accuracy metrics for the closest-neighbor function"
  ([]
     (accuracy validation-set training-set))
  ([validations trainers]
     (let [total (count validations)
           closest (closest-neighbor (:pixels (first validations)) trainers)
           correct (count (filter true? (list (correct? (first validations) closest))))]
       {:total total
        :correct correct
        :percentage (if (zero? total) 0.0 (/ correct total))})))

(defn performance
  "calculates performance metrics for the closest-neighbor function"
  []
  {:time (time (closest-neighbor [] []))})
