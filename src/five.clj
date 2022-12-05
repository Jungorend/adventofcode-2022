(ns five
  (:require [clojure.string :as s]))

(defn read-input
  []
  (s/split (slurp "resources/five") #"\n\n"))

(defn normalize-values
  [line]
  (map #(nth % 1)
       (partition 4 (str line " "))))

(defn push-line
  ([line coll]
   (push-line line coll []))
  ([line coll result]
   (if (empty? coll)
     result
     (recur (rest line) (rest coll)
            (conj result (if (= \space (first line))
                           (first coll)
                           (conj (first coll) (first line))))))))

(defn txt->cargo
  [txt]
  (let [s (drop-last (s/split txt #"\n"))
        c (take (count (normalize-values (first s))) (repeat []))]
    (reduce (fn [cargo line] (push-line (normalize-values line) cargo))
            c s)))

(txt->cargo (first (read-input)))
