(ns interest-is-interesting)

(defn interest-rate
  "Returns the interest rate based on the specified balance."
  [balance]
  (cond
    (< balance 0) -3.213
    (< balance 1000) 0.5
    (< balance 5000) 1.621
    :else 2.475))
(interest-rate 200.75M)
(interest-rate -0.75M)
(interest-rate 1500M)
(interest-rate 5000M)

(defn interest-factor
  [balance]
  (bigdec (/ (interest-rate balance) 100.0M)))
(interest-factor 1600M)

(defn annual-balance-update
  "Returns the annual balance update, taking into account the interest rate."
  [balance]
  (let [factor (interest-factor balance)
        grow-factor (+ 1.0M (abs factor))] 
    (* grow-factor balance)
    ))
  (annual-balance-update 200.75M)
  (annual-balance-update -152964.231M)

  (def balance 550.5M)
  (def tax-free-percentage 2.5)
  (defn amount-to-donate
    "Returns how much money to donate based on the balance and the tax-free percentage."
    [balance tax-free-percentage]
    (if (> balance 0)
      (int (* balance 0.01 2 tax-free-percentage))
      0))
  (amount-to-donate balance tax-free-percentage)
