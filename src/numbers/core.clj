;; TODO: map->pmap
(ns numbers.core
  (:require
   [numbers.maths :refer [euclidean-distance]]))

(defn dist [x y] (euclidean-distance x y))

(defn closest-neighbors
  "a machine learning classifier which returns a lazy-seq of the
   closest neighbors sorted by distance."
  ([unknown knowns]
     (closest-neighbors unknown knowns Integer/MAX_VALUE {}))
  ([unknown knowns best-score best-match]
     (if (and (seq knowns) (not (zero? best-score)))
       (let [score (dist (:pixels (first knowns)) unknown)]
         (if (< score best-score)
           (recur unknown (rest knowns) score (first knowns))
           (recur unknown (rest knowns) best-score best-match)))
       (list {:best-score best-score
              :best-match best-match}))))

(defn nth-closest-neighbor
  ""
  [n unknown knowns]
  (cond
   (= 1 n) (first (closest-neighbors unknown knowns))
   :else (take n (closest-neighbors unknown knowns))))

(defn closest-neighbor
  "a machine learning classifier which identifies the closest neighbor
   based on a distance function."
  [unknown knowns]
  (first (closest-neighbors unknown knowns)))
