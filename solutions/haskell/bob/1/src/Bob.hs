{-# LANGUAGE OverloadedStrings #-}

module Bob (responseFor) where

import qualified Data.Text as T
import           Data.Text (Text)
import           Data.Char (isUpper, isLetter)

responseFor :: Text -> Text
responseFor xs 
    | vraag && all_hoofdletters = "Calm down, I know what I'm doing!"
    | vraag = "Sure."
    | all_hoofdletters = "Whoa, chill out!"
    | T.null $ T.strip xs = "Fine. Be that way!"
    | otherwise = "Whatever."
    where stripped = T.strip xs
          letters = T.filter isLetter stripped
          all_hoofdletters = T.length letters > 0 && T.all isUpper letters
          vraag = T.isSuffixOf "?" stripped