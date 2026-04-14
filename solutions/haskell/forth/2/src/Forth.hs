{-# LANGUAGE OverloadedStrings #-}

module Forth
  ( ForthError (..),
    ForthState,
    evalText,
    toList,
    emptyState,
  )
where

import Control.Monad (foldM)
import qualified Data.Map as M
import Data.Maybe (fromMaybe)
import Data.Text (Text)
import qualified Data.Text as T
import qualified Data.Text.Read as TR

data ForthError
  = DivisionByZero
  | StackUnderflow
  | InvalidWord
  | UnknownWord Text
  deriving (Show, Eq)

data ForthState = ForthState
  { stack :: [Int],
    symbols :: M.Map T.Text [Text] -- key = Text, value = [Text]
  }
  deriving (Show)

emptyState :: ForthState
emptyState = ForthState [] M.empty

evalText :: Text -> ForthState -> Either ForthError ForthState
evalText text state =
  let text_words = T.words $ T.toLower text
   in if head text_words == ":"
        then evalSymbolText text_words state
        else foldM (flip fetchTextPart) state text_words

evalSymbolText :: [T.Text] -> ForthState -> Either ForthError ForthState
evalSymbolText text_words state =
  let key_values = skipEnds text_words
      key = head key_values
   in if isInteger key
        then Left (InvalidWord)
        else
          let values = tail key_values
              newValues = concatMap (replaceSymbolValue state) values
           in Right $ insertSymbol key newValues state

-- Replace "symbolic values" from incoming symbols by real values
replaceSymbolValue :: ForthState -> T.Text -> [T.Text]
replaceSymbolValue state value =
  if isInteger value
    then [value]
    else do
      Data.Maybe.fromMaybe [value] (getSymbol value state)

insertSymbol :: T.Text -> [T.Text] -> ForthState -> ForthState
insertSymbol key values state =
  state {symbols = M.insert key values (symbols state)}

getSymbol :: T.Text -> ForthState -> Maybe [Text]
getSymbol key state = M.lookup key (symbols state)

skipEnds :: [T.Text] -> [T.Text]
skipEnds [] = []
skipEnds [_] = []
skipEnds [_, _] = []
skipEnds (_ : key_values) = init key_values

fetchTextPart :: Text -> ForthState -> Either ForthError ForthState
fetchTextPart text state =
  case getSymbol text state of
    Just values -> foldM (flip evalTextPart) state values
    Nothing -> evalTextPart text state

evalTextPart :: Text -> ForthState -> Either ForthError ForthState
evalTextPart text state
  | text == "+" = binOperation state (+)
  | text == "-" = binOperation state (-)
  | text == "*" = binOperation state (*)
  | text == "/" = divide state
  | text == "dup" = dup state
  | text == "drop" = Forth.drop state
  | text == "swap" = swap state
  | text == "over" = over state
  | isInteger text = case (parseInt text) of 
                        Right n -> Right $ push n state
                        Left _ -> Left InvalidWord
  | otherwise = Left (UnknownWord text)

dup :: ForthState -> Either ForthError ForthState
dup state =
  do
    v <- peek state
    Right (push v state)

swap :: ForthState -> Either ForthError ForthState
swap state =
  do
    (v1, s1) <- pop state
    (v2, s2) <- pop s1
    let s3 = push v1 s2
    Right (push v2 s3)

over :: ForthState -> Either ForthError ForthState
over state =
  do
    (v1, s1) <- pop state
    (v2, s2) <- pop s1
    let s3 = push v2 s2
    let s4 = push v1 s3
    Right (push v2 s4)

drop :: ForthState -> Either ForthError ForthState
drop state =
  do
    (_, s1) <- pop state
    Right s1

binOperation :: ForthState -> (Int -> Int -> Int) -> Either ForthError ForthState
binOperation state operation =
  do
    (g1, s1) <- pop state
    (g2, s2) <- pop s1
    let result = operation g2 g1
    Right (push result s2)

divide :: ForthState -> Either ForthError ForthState
divide state =
  do
    (g1, s1) <- pop state
    (g2, s2) <- pop s1
    if g1 == 0
      then Left DivisionByZero
      else Right (push (div g2 g1) s2)

isInteger :: T.Text -> Bool
isInteger txt =
  case (TR.decimal txt :: Either String (Int, T.Text)) of
    Right (_, rest) -> T.null rest -- parsing gelukt en niets over
    Left _ -> False

parseInt :: T.Text -> Either Text Int
parseInt txt =
  case TR.decimal txt of
    Right (n, rest) | T.null rest -> Right n
    _                             -> Left "Geen geldige Int"

toList :: ForthState -> [Int]
toList state =
  let stackLijst = stackList state
   in reverse stackLijst

-- Stack helperfuncties

push :: Int -> ForthState -> ForthState
push x state = state {stack = x : stack state}

pop :: ForthState -> Either ForthError (Int, ForthState)
pop (ForthState [] _) = Left StackUnderflow
pop (ForthState (x : xs) symb) = Right (x, ForthState xs symb)

peek :: ForthState -> Either ForthError Int
peek (ForthState [] _) = Left StackUnderflow
peek (ForthState (x : _) _) = Right x

stackList :: ForthState -> [Int]
stackList (ForthState s _) = s
