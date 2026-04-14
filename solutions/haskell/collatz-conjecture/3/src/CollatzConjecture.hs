module CollatzConjecture (collatz) where

collatz :: Integer -> Maybe Integer
collatz n
  | n > 0 = Just (countSteps n)
  | otherwise = Nothing

countSteps :: Integer -> Integer
countSteps = toInteger . length . takeWhile (/= 1) . iterate nextStep

nextStep :: Integer -> Integer
nextStep k =
  if even k
    then k `div` 2
    else 3 * k + 1
