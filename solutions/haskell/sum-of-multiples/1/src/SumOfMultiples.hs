module SumOfMultiples (sumOfMultiples) where

import qualified Data.Set as S

sumOfMultiples :: [Integer] -> Integer -> Integer
sumOfMultiples factors limit = sum . S.fromList $ sumOfMultiples' factors limit []

sumOfMultiples' :: [Integer] -> Integer -> [Integer] -> [Integer]
sumOfMultiples' [] _ acc = acc
sumOfMultiples' (x:xs) limit acc = sumOfMultiples' xs limit multiples <> acc
    where multiples = case x of
            0 -> [0]
            _ -> [x, 2 * x .. limit - 1]