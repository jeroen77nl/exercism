(import (rnrs))


(define (leap-year? year)
  (define (jaar-deelbaar? n)
    (= (mod year n) 0))
  (and (jaar-deelbaar? 4)
       (or (not (jaar-deelbaar? 100))
           (jaar-deelbaar? 400))))

