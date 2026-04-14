module Pangram (isPangram) where

import qualified Data.Set as S
import qualified Data.Char as C

isPangram :: String -> Bool
isPangram text = let letters = S.fromList $ normalize text
                  in S.size letters == 26

normalize :: String -> String
normalize = filter C.isAsciiLower . map C.toLower
