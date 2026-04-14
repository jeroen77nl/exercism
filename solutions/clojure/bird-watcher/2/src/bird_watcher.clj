(ns bird-watcher)

(def last-week
  [0 2 5 3 7 8 4])

(def birds-per-day [2 5 0 7 4 1])

(defn today [birds]
  (peek birds))
(today birds-per-day)

(defn inc-bird [birds]
  (conj (pop birds) (inc (peek birds))))
(inc-bird birds-per-day)

(defn day-without-birds? [birds]
  (or (some zero? birds)
      false))
(day-without-birds? birds-per-day)

(defn n-days-count [birds n]
  (apply + (take n birds)))
(n-days-count birds-per-day 4)

(defn busy-days [birds]
;;   (count (filter (fn [x] (>= x 5)) birds)))
  (count (filter #(>= %1 5) birds)))
(busy-days birds-per-day)

(defn odd-week? [week]
  (defn odd-week-iter [i]
    (cond (> i 5) true
          (and (= (get week i) 0)
               (= (get week (+ i 1)) 1)) (odd-week-iter (+ i 1))
          (and (= (get week i) 1)
               (= (get week (+ i 1)) 0)) (odd-week-iter (+ i 1))
          :else false))
  (odd-week-iter 0))

(odd-week? [1 0 1 0 1 0 1])