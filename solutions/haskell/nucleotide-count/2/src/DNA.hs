module DNA (nucleotideCounts, Nucleotide(..)) where

import Data.Map (Map)
import qualified Data.Map as M

data Nucleotide = A | C | G | T deriving (Eq, Ord, Show)

nucleotideCounts :: String -> Either String (Map Nucleotide Int)
nucleotideCounts xs =
    let aantalA = length [ a | a <- xs, a == 'A' ]
        aantalC = length [ c | c <- xs, c == 'C' ]
        aantalG = length [ g | g <- xs, g == 'G' ]
        aantalT = length [ t | t <- xs, t == 'T' ]
    in  if aantalA + aantalC + aantalG + aantalT == length xs
        then Right $ M.fromList[(A, aantalA), (C, aantalC), (G, aantalG), (T, aantalT)]
        else Left "error"
