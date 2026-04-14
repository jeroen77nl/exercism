(ns acronym
  (:require [clojure.string :as str]))

(defn first-upper [s]
  (if (empty? s)
    s
    (str (str (str/upper-case (first s)))
         (apply str (rest s)))))

(defn acronym
  [phrase]
  (let [woorden (str/split phrase #"[^A-z]")
        woorden-upper (map first-upper woorden)
        hoofdletter-groepen (flatten (map #(str/split % #"[a-z]") woorden-upper))
        eerste-letters (map first hoofdletter-groepen)] 
   (apply str eerste-letters))) 
