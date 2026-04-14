(import (rnrs))

(define (deelbaar? x y)
  (= (mod x y) 0))

(define (leap-year? year)
  (and (deelbaar? year 4)
       (or (not (deelbaar? year 100))
           (deelbaar? year 400))))

