(ns two
  (:require [clojure.string :as s]))

(def scores
  {:rock 1
   :paper 2
   :scissors 3
   :lose 0
   :draw 3
   :win 6})

(defn decipher-guess
  [value]
  (cond
    (or (= "A" value) (= "X" value)) :rock
    (or (= "B" value) (= "Y" value)) :paper
    :else :scissors))

(defn round-score
  [opponent player]
  (let [opp (decipher-guess opponent)
        pl  (decipher-guess player)
        win-points (cond
                     (= opp pl) (:draw scores)
                     (= opp :rock) (if (= :paper pl)
                                     (:win scores)
                                     (:lose scores))
                     (= opp :paper) (if (= :scissors pl)
                                      (:win scores)
                                      (:lose scores))
                     (= opp :scissors) (if (= :rock pl)
                                         (:win scores)
                                         (:lose scores)))]
    (+ win-points (get scores pl))))

(defn decipher-guess-two
  [line]
  (let [[left right] (s/split line #" ")
        opponent (case left
                   "A" 1
                   "B" 2
                   "C" 3)
        match-goal (case right
                     "X" 2
                     "Y" 0
                     "Z" 1)
        match-points (case right
                       "X" 0
                       "Y" 3
                       "Z" 6)
        cycle-amount (+ opponent match-goal)
        player-choice (last (take cycle-amount (cycle [:rock :paper :scissors])))]
    (+ match-points (get scores player-choice))))

(reduce (fn [acc i] (+ acc (decipher-guess-two i)))
        0
        (s/split (slurp "inputs/two") #"\n"))
