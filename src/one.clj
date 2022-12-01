(ns one
  (:require [clojure.string :as s]))

(def input (s/split (slurp "inputs/one") #"\n"))

(defn first-elf
  [x]
  (loop [elf []
         others x]
    (cond (empty? others) [elf nil]
          (= "" (first others)) [elf (rest others)]
          :else (recur (conj elf (Integer/parseInt (first others))) (rest others)))))

(defn elves
  [x]
  (loop [r []
         i x]
    (if (empty? i)
      r
      (let [elf (first-elf i)]
        (recur (conj r (first elf)) (second elf))))))

(defn elf-totals
  [x]
  (map #(apply + %) x))

(->> (elves input)
     elf-totals
     (sort >)
     first)

;; Part Two, just need to add together the three
(->> (elves input)
     elf-totals
     (sort >)
     (take 3)
     (apply +))
