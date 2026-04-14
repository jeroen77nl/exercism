(ns bird-watcher)

(def last-week 
  [0 2 5 3 7 8 4]
  )

(defn last-index [arr]
  (- (count arr) 1)
  )

(def birds-per-day [2 5 0 7 4 1])
(defn today [birds]
  (get birds (last-index birds)))
(today birds-per-day)


(defn inc-bird [birds]
  (assoc birds 
         (last-index birds) 
         (+ (today birds) 1))
   )
(inc-bird birds-per-day)

(defn day-without-birds? [birds]
  (defn birds-loop [i]
    (cond  (= i (count birds)) false
           (= (get birds i) 0) true
           :else (birds-loop (+ i 1))))
  (birds-loop 0))
(day-without-birds? birds-per-day)

(defn n-days-count [birds n]
  (defn birds-som [i]
   (cond  (> i n) 0
          :else (+ (get birds (- i 1)) 
                   (birds-som (+ i 1))
                   )))
 (birds-som 1)
  )
(n-days-count birds-per-day 4)

(defn busy-days [birds]
  (defn busy-days-iter [i]
   (cond  (> i 5) 0
          (>= (get birds i) 5) (+ 1 (busy-days-iter (+ i 1)))
          :else (busy-days-iter (+ i 1)))) 
  (busy-days-iter 1)
  )
(busy-days birds-per-day)

(defn odd-week? [week]
  (defn odd-week-iter [i]
    (cond (> i 5) true
          (and (= (get week i) 0)
               (= (get week (+ i 1)) 1)) (odd-week-iter (+ i 1))
          (and (= (get week i) 1)
               (= (get week (+ i 1)) 0)) (odd-week-iter (+ i 1))
          :else false)
          )
  (odd-week-iter 0)
  )

(odd-week? [1 0 1 0 1 0 1])