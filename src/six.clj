(ns six
  (:require [clojure.string :as s]))

(defn read-input
  []
  (slurp "resources/six"))

(defn part-one
  []
  (loop [signal (read-input)
         input (apply conj (clojure.lang.PersistentQueue/EMPTY) (take 4 signal))
         iteration 4]
    (if (= 4 (count (set input)))
      iteration
      (recur (drop 1 signal) (conj (pop input) (nth signal 4)) (inc iteration)))))

(defn part-two
  []
  (loop [signal (read-input)
         input (apply conj (clojure.lang.PersistentQueue/EMPTY) (take 14 signal))
         iteration 14]
    (if (= 14 (count (set input)))
      iteration
      (recur (drop 1 signal) (conj (pop input) (nth signal 14)) (inc iteration)))))
