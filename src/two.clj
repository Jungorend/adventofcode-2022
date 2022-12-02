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
