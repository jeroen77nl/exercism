(ns cars-assemble)

(def gross-production-per-hour 221)

(defn success-rate
  [speed]
  (cond (= speed 0) 0.0
        (<= speed 4) 1.0
        (<= speed 8) 0.90
        (<= speed 9) 0.8
        :else 0.77))

(* gross-production-per-hour 5)

(defn production-rate
  "Returns the assembly line's production rate per hour,
   taking into account its success rate"
  [speed]
  (* (* gross-production-per-hour
        speed)
     (success-rate speed)))

(production-rate 6)

(defn working-items
  "Calculates how many working cars are produced per minute"
  [speed]
  (int 
   (/ (production-rate speed) 60)
   )
  )

(working-items 6)
