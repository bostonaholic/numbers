(ns numbers.metrics
  (:require
   [numbers.parser :refer [parse-file]]
   [numbers.core :refer :all]))

(defonce validation-set (parse-file "validationset.csv")) ;; 500 examples
(defonce training-set (parse-file "trainingset.csv")) ;; 5,000 examples

(defn correct?
  "returns if the nearest neighbor found is the correct label"
  [observation found]
  (and (= (:label observation) (:label (:match found)))
       (not (nil? (:label (:match found))))))

(defn accuracy
  "calculates accuracy metrics for the nearest-neighbor function"
  ([]
     (accuracy validation-set training-set))
  ([validations trainers]
     (let [total (count validations)
           nearests (map #(naive-nearest-neighbor (:pixels %) trainers) validations)
           corrects (map correct? validations nearests)
           correct (count (filter true? corrects))]
       {:total total
        :correct correct
        :percentage (float (if (zero? total) 0 (/ correct total)))
        :trained-on (count trainers)})))

(defn stepped-accuracy
  "steps through the accuracy function loading different sizes of data sets"
  []
  (do
    (println (accuracy (take 100 validation-set) (take 500 training-set)))
    (println (accuracy (take 100 validation-set) (take 1000 training-set)))
    (println (accuracy (take 100 validation-set) (take 3000 training-set)))
    (println (accuracy (take 100 validation-set) training-set))
    (println (accuracy validation-set (take 500 training-set)))
    (println (accuracy validation-set (take 1000 training-set)))
    (println (accuracy validation-set (take 2000 training-set)))
    (println (accuracy validation-set (take 3000 training-set)))
    (println (accuracy validation-set training-set))))

(defn performance
  "calculates performance metrics for the nearest-neighbor function"
  []
  {:time (time (naive-nearest-neighbor [] []))})
