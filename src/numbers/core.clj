;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.maths :refer [euclidean-distance]]))

(defn dist [x y] (euclidean-distance x y))

(defn sort-by-score
  "sort a collection of maps by the :score key"
  [coll]
  (sort #(< (:score %1) (:score %2)) coll))

(defn nearest-neighbors
  "a machine learning classifier which returns a lazy-seq of the
   nearest neighbors sorted by distance."
  ([unknown knowns]
     (nearest-neighbors unknown knowns Integer/MAX_VALUE {}))
  ([unknown knowns best-score best-match]
     (if (and (seq knowns) (not (zero? best-score)))
       (let [score (dist (:pixels (first knowns)) unknown)]
         (if (< score best-score)
           (recur unknown (rest knowns) score (first knowns))
           (recur unknown (rest knowns) best-score best-match)))
       (list {:best-score best-score
              :best-match best-match}))))

(defn k-nearest-neighbor
  ""
  [n unknown knowns]
  (cond
   (= 1 n) (first (nearest-neighbors unknown knowns))
   :else (take n (nearest-neighbors unknown knowns))))

(defn nearest-neighbor
  "a machine learning classifier which identifies the nearest neighbor
   based on a distance function."
  [unknown knowns]
  (first (nearest-neighbors unknown knowns)))
