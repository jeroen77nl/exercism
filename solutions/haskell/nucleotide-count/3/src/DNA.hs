module DNA (nucleotideCounts, Nucleotide (..)) where

import Data.Char (toUpper)
import Data.Map (Map)
import qualified Data.Map as M

data Nucleotide = A | C | G | T deriving (Eq, Ord, Show, Read)

nucleotideCounts :: String -> Either String (Map Nucleotide Int)
nucleotideCounts xs
  | all (`elem` "ACGT") xs = Right $ M.fromListWith (+) [(read [toUpper c], 1) | c <- xs]
  | otherwise = Left xs
