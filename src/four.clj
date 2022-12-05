(ns four
  (:require [clojure.string :as s]))

(defn read-input
  []
  (let [f (s/split (slurp "resources/four") #"\n")]
    (map #(s/split % #",") f)))

(defn ranges->values
  [elf1 elf2]
  (let [[min1 max1] (s/split elf1 #"-")
        [min2 max2] (s/split elf2 #"-")]
    (map #(Integer/parseInt %) [min1 max1 min2 max2])))

(defn range-in-range?
  [[elf1 elf2]]
  (let [[min1 max1 min2 max2] (ranges->values elf1 elf2)]
    (cond (= min1 min2) true
          (< min1 min2) (<= max2 max1)
          :else (<= max1 max2))))

(count (filter range-in-range? (read-input)))

;; Part Two
(defn overlapping-range?
  [[elf1 elf2]]
  (let [[min1 max1 min2 max2] (ranges->values elf1 elf2)]
    (cond (= min1 min2) true
          (< min1 min2) (>= max1 min2)
          :else (>= max2 min1))))

(count (filter overlapping-range? (read-input)))
