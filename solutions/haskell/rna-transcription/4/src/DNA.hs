module DNA (toRNA) where

toRNA :: String -> Either Char String
toRNA dna = traverse convert dna

convert :: Char -> Either Char Char
convert c = case c of
    'G' -> Right 'C'
    'C' -> Right 'G'
    'T' -> Right 'A'
    'A' -> Right 'U'
    _ -> Left c
