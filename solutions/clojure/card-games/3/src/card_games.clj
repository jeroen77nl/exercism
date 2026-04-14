(ns card-games)

(defn rounds
  "Takes the current round number and returns 
   a `list` with that round and the _next two_."
  [n]
  (list n (+ n 1) (+ n 2)))
(rounds 27)

(defn concat-rounds
  "Takes two lists and returns a single `list` 
   consisting of all the rounds in the first `list`, 
   followed by all the rounds in the second `list`"
  [l1 l2]
  (flatten (cons l1 l2)))
(concat-rounds '(27 28 29) '(35 36))

(defn bevat?
  [l n]
  (if (empty? l)
    false
    (if (= n (first l))
      true
      (bevat? (rest l) n))))
(bevat? (list 1 2 3) 2)

(defn contains-round?
  "Takes a list of rounds played and a round number.
   Returns `true` if the round is in the list, `false` if not."
  [l n]
  (if (nil? (some #{n} l))
    false
    true))

(defn card-average
  "Returns the average value of a hand"
  [hand]
  (double (/ (apply + hand) (count hand))))

(defn median [ns]
  (let [imedian (quot (count ns) 2)]
   (double (nth ns imedian))))

(defn avg-first-last [ns]
  (double (/ (+ (first ns) (last ns)) 2)))

(defn approx-average?
  "Returns `true` if average is equal to either one of:
  - Take the average of the _first_ and _last_ number in the hand.
  - Using the median (middle card) of the hand."
  [hand]
  (or (= (card-average hand) ( median hand))
      (= (card-average hand) ( avg-first-last hand))))

(defn geef-even-oneven-pos
  [hand i f-even-oneven]
  (if (empty? hand)
    nil
    (if (f-even-oneven i) 
      (cons (first hand) (geef-even-oneven-pos (rest hand) (+ i 1) f-even-oneven))
      (geef-even-oneven-pos (rest hand) (+ i 1) f-even-oneven))))

(defn average-van-lijst
  [hand] 
  (let [som (apply + hand)
        n (count hand)]
    (/ som n)
    ))

(def avg-even-oneven (comp average-van-lijst geef-even-oneven-pos))

(defn average-even-odd?
  "Returns true if the average of the cards at even indexes 
   is the same as the average of the cards at odd indexes."
  [hand]
  (= (avg-even-oneven hand 0 even?)
     (avg-even-oneven hand 0 odd?)))

(defn maybe-double-last
  "If the last card is a Jack (11), doubles its value
   before returning the hand."
  [hand]
  (if (empty? hand)
    '()
    (if (and (= (count hand) 1)
             (= (first hand) 11))
      (cons (* 2 (first hand)) (maybe-double-last (rest hand)))
      (cons (first hand) (maybe-double-last (rest hand))))
    ))

