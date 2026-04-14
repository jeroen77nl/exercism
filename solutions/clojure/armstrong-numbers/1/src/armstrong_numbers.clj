(ns armstrong-numbers
  (:require [clojure.math :as math]))

(defn digits [n]
  (if (pos? n)
    (conj (digits (quot n 10)) (mod n 10))
    []))

(defn armstrong? [num]
  (let [exp (count (str num))
        sum (apply + (map #(math/pow %1 exp)
                          (digits num)))]
    (== (int sum) num))
  )
  
(armstrong? 9)