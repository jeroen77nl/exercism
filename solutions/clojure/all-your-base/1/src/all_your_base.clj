(ns all-your-base
  (:require [clojure.math :as math]))

(defn make-power [base] 
  ;; create function that can be used in a map-indexed function
  (fn [idx itm]
    (* itm (int (math/pow base idx)))))

((make-power 2) 3 4)

(defn to-dec [base-from lon]
  (apply + (map-indexed (make-power base-from) (reverse lon))))

(to-dec 2 '(1 1 1 0)) 
(to-dec 4 '(1 1 1 0)) 
(to-dec 10 '(1 1))
(map-indexed (make-power 2) (reverse '(1 1 1 0)))
(map-indexed (make-power 2) '(0 1 1 1))

(defn str-to-int [lon]
  (Integer/parseInt (apply str lon)))

(str-to-int (list 1 2 3))

(defn from-dec' [dec base result]
  (if (= dec 0)
    result
    (from-dec' (quot dec base) 
               base 
               (cons (mod dec base) result))))

(from-dec' 14 18 (list))

(defn from-dec [base-to dec]
  (if (= dec 0)
    '(0)
    (from-dec' dec
               base-to
               (list))))

(from-dec 14 0)

(defn convert [base-from lon base-to]
  (cond (< base-from 2) nil
        (< base-to 2) nil
        (> (count (filter neg? lon)) 0) nil
        (> (count (filter #(<= base-from %) lon)) 0) nil
        (empty? lon) lon
        :else
        (from-dec base-to
                  (to-dec base-from lon))))

(convert 10 '(1 1) 2)
(convert 2 '(1 0 1 1) 10)

(convert 2 '(1 0 1 0) 4)
(convert 4 '(2 2) 2)

(convert 2 '(1 0 1) 10)

(convert -2 '(1) 7)