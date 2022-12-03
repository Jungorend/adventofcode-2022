(ns three
  (:require [clojure.string :as s])
  (:use [clojure.set]))

(defn read-input []
  (s/split (slurp "resources/three") #"\n"))

(defn unique-letter
  [str]
  (let [[a b] (split-at (/ (count str) 2) str)]
    (first (intersection (set a) (set b)))))

(defn calculate-priority
  [letter]
  (let [val (int letter)]
    (if (> val 90)
      (- val 96)    ; "zeroes" to 1
      (- val 38)))) ; "zeroes" to 27

(defn sum
  [f coll]
  (reduce #(+ %1 (f %2)) 0 coll))

(sum #(calculate-priority (unique-letter %))
     (read-input))

;; part two
(defn unique-badge
  [coll]
  (let [elves (map set (take 3 coll))]
    (first (apply intersection elves))))

(defn sum-priorities
  [coll result]
  (if (empty? coll)
    result
    (recur (drop 3 coll) (+ result (calculate-priority (unique-badge coll))))))

(sum-priorities (read-input) 0)
