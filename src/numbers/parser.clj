(ns numbers.parser
  (:require
   [clojure.string :as str]
   [clojure.java.io :refer [resource reader]]))

(defn split-to-integers
  "returns a list of integers from a comma separated String"
  [line]
  (map #(Integer/parseInt %) (str/split line #",")))

(defn read-file
  "returns a lazy-seq of a file in the format specified in the project spec"
  [filename]
  (line-seq (reader (resource filename))))

(defn parse-line [line]
  {:label (first line)
   :pixels (rest line)})

(defn parse-file
  ""
  [filename]
  (map parse-line
       (map split-to-integers (rest (read-file filename)))))
