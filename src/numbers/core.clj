;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.maths :refer [euclidean-distance]]))

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbor
  "a machine learning classifier which identifies the closest neighbor
   based on a distance function."
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
