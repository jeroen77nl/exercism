module CollatzConjecture (collatz) where

collatz :: Integer -> Maybe Integer
collatz n
  | n < 1 = Nothing
  | otherwise = collatzIter n 0

collatzIter :: Integer -> Integer -> Maybe Integer
collatzIter n i
  | n == 1 = Just i
  | even n = collatzIter (n `div` 2) (i + 1)
  | otherwise = collatzIter (n * 3 + 1) (i + 1)
