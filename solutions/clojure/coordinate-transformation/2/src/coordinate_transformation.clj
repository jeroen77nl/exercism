(ns coordinate-transformation)

(def increment
  (let [counter (atom 0)]
    (fn [] (swap! counter inc))))
(increment)

(defn translate2d 
  "Returns a function making use of a closure to
   perform a repeatable 2d translation of a coordinate pair."
  [dx dy]
  (let [x (atom dx)
        y (atom dy)]
    (fn
      [dx2 dy2]
      [(swap! x + dx2) (swap! y + dy2)])
    )
  )
((translate2d 2 0) 4 8)

(defn scale2d
  "Returns a function making use of a closure to
   perform a repeatable 2d scale of a coordinate pair."
  [sx sy]
  (let [x (atom sx)
        y (atom sy)]
    (fn
      [dx dy]
      [(swap! x * dx) (swap! y * dy)])))
((scale2d 2 2) 6 -3)

(defn compose-transform
  [f g]
  (comp (partial apply g) f))

(def move-coordinates-right-2px (translate2d 2 0))
(def double-coordinates (scale2d 2 2))
(def composed-transformations
  (compose-transform move-coordinates-right-2px
                     double-coordinates))
(def result (composed-transformations 0 1))
result

(defn memoize-transform
  "Returns a function that memoizes the last result.
   If the arguments are the same as the last call,
   the memoized result is returned."
  [f] 
  (let [save_f (atom f)
        args (atom [])
        result (atom ())]
    (fn
      [& args']
      (if (= @args args')
        @result
        (let [r (apply @save_f args')]
          (reset! args args')
          (reset! result r)
          r
          )))))

(def triple-scale (scale2d 3 3))
(def memoized-scale (memoize-transform triple-scale))
(memoized-scale 2 2)