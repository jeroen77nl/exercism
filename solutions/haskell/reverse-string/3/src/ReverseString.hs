module ReverseString (reverseString) where

reverseString :: String -> String
reverseString s = 
    let f acc elem = elem : acc
     in foldl f [] s


