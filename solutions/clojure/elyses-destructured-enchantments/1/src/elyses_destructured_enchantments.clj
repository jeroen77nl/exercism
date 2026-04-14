(ns elyses-destructured-enchantments)

(defn first-card
  "Returns the first card from deck."
  [deck]
  (let [[top] deck]
    top))

(defn second-card
  "Returns the second card from deck."
  [deck]
  (let [[_ second] deck]
    second))

(defn swap-top-two-cards
  "Returns the deck with first two items reversed."
  [deck]
  (let [[fst snd & tail] deck]
    (vec (flatten (list snd fst tail)))))
(def deck [10 7 3 8 5])
(swap-top-two-cards deck)

(defn discard-top-card
  "Returns a sequence containing the first card and
   a sequence of the remaining cards in the deck."
  [deck]
  (let [[fst & tail] deck]
    [fst (vec tail)]))

(def face-cards
  ["jack" "queen" "king"])

(defn insert-face-cards
  "Returns the deck with face cards between its head and tail."
  [deck]
  (let [[fst & tail] deck]
    (concat [fst] face-cards tail)))