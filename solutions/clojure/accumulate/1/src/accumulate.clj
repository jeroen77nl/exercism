(ns accumulate)

(defn accumulate 
  [f c]
  (if (empty? c)
    []
    (cons (f (first c)) (accumulate f (rest c))))
)

(accumulate inc [1 2 3])
(accumulate inc [])