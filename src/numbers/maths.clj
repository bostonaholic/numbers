(ns numbers.maths)

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
