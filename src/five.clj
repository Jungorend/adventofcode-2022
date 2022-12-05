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
        c (take (count (normalize-values (last s))) (repeat []))]
    (reduce (fn [cargo line] (push-line (normalize-values line) cargo))
            c s)))

(defn display-top-crates
  [cargo]
  (reduce #(if (empty? %2) (str %1 " ") (str %1 (first %2)))
          "" cargo))

(defn update-cargo
  [line cargo]
  (let [regex (re-find #"move (\d+) from (\d+) to (\d+)" line)
        [amount from to] (map #(Integer/parseInt %) (rest regex))
        crates-to-move (apply vector (reverse (take amount (nth cargo (dec from)))))]
    (-> cargo
        (update (dec from) #(drop amount %))
        (update (dec to) #(apply conj crates-to-move %)))))

(defn part-one
  []
  (let [input (read-input)
        cargo (txt->cargo (first input))]
    (display-top-crates (reduce #(update-cargo %2 %1) cargo (s/split (second input) #"\n")))))

;; Part Two
;; Identical to one except I took out the earlier reverse function
(defn update-cargo-two
  [line cargo]
  (let [regex (re-find #"move (\d+) from (\d+) to (\d+)" line)
        [amount from to] (map #(Integer/parseInt %) (rest regex))
        crates-to-move (apply vector (take amount (nth cargo (dec from))))]
    (-> cargo
        (update (dec from) #(drop amount %))
        (update (dec to) #(apply conj crates-to-move %)))))

(defn part-two
  []
  (let [input (read-input)
        cargo (txt->cargo (first input))]
    (display-top-crates (reduce #(update-cargo-two %2 %1) cargo (s/split (second input) #"\n")))))
