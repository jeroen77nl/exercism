(ns squeaky-clean 
  (:require [clojure.string :as str]))

(defn clean-space
  [s]
  (str/replace s #" " "_") 
  )

(defn clean-ctrl
  [s]
  (if (empty? s) 
    ""
    (let [f (int (first s))]
         (if (or (and (>= f (int \u0000))
                      (<= f (int \u001F)))
                 (and (>= f (int \u007F))
                      (<= f (int \u009F))))
           (apply str "CTRL" (clean-ctrl (rest s)))
           (apply str (first s) (clean-ctrl (rest s)))))))
(clean-ctrl "my\u007FId")

(defn kebab-to-camel
  [s]
  (if (empty? s)
    ""
    (let [f (first s)
          r (rest s)]
      (if (= f \-)
        (let [f2 (first r)
              r2 (rest r)]
          (str (str/upper-case f2) (kebab-to-camel r2)))
        (str f (kebab-to-camel r))))))  

(kebab-to-camel "abc-def-ghi")

(defn letter-of-underscore? [c]
  (or (Character/isLetter c)
      (= c \_)))
(letter-of-underscore? \Ë)

(defn keep-letters 
 [s]
 (if (empty? s)
   ""
   (let [f (first s)]
     (if (letter-of-underscore? f)
      (str f (keep-letters (rest s)))
       (keep-letters (rest s)))
     )))
(keep-letters "abc987def000__x")

(defn omit-greek
  [s]
 (if (empty? s)
   ""
   (let [f (int (first s))]
     (if (and (>= f (int \α))
              (<= f (int \ω)))
       (omit-greek (rest s))
       (str (first s) (omit-greek (rest s)))))))
(omit-greek "MyΟβιεγτFinder")

(defn clean
  "TODO: add docstring"
  [s]
  (omit-greek
   (keep-letters
    (kebab-to-camel
     (clean-ctrl
      (clean-space s)))))
  )

(clean "my   Id")
(clean  "à-ḃç-D\001E")