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
    (concat [snd fst] tail)))

(defn discard-top-card
  "Returns a sequence containing the first card and
   a sequence of the remaining cards in the deck."
  [deck]
  (let [[fst & tail] deck]
    (if (nil? tail)
      [fst tail]
      (concat [fst (vec tail)]))))

(def face-cards
  ["jack" "queen" "king"])

(defn insert-face-cards
  "Returns the deck with face cards between its head and tail."
  [deck]
  (let [[fst & tail] deck]
    (if (nil? fst)
      (concat face-cards tail)
      (concat [fst] face-cards tail))))