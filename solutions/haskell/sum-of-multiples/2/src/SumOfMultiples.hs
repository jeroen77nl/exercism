module SumOfMultiples (sumOfMultiples) where

import qualified Data.Set as S

sumOfMultiples :: [Integer] -> Integer -> Integer
sumOfMultiples factors limit = sum . S.fromList $ allMultiples factors limit

allMultiples :: [Integer] -> Integer -> [Integer]
allMultiples factors limit = 
    foldr ((<>) . multiples limit) [] factors

multiples :: (Eq a, Num a, Enum a) => a -> a -> [a]
multiples limit factor = case factor of
             0 -> [0]
             _ -> [factor, 2 * factor .. limit - 1]