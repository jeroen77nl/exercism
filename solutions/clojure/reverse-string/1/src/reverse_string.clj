(ns reverse-string)

(defn reverse-string [s]
  (defn reverse-iter [sold snew]
    (if (empty? sold)
      snew
      (str (reverse-iter (rest sold)
                         (str (first sold)
                              snew)))))
  (reverse-iter s ""))

(reverse-string "abcde")