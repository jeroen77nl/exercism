module Darts (score) where

score :: Float -> Float -> Int
score x y
  | r > 10.0 = 0
  | r > 5.0 = 1
  | r > 1.0 = 5
  | otherwise = 10
  where
    r = sqrt (x * x + y * y)
