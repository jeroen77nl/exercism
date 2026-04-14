{-# LANGUAGE OverloadedStrings #-}

module Forth
  ( ForthError (..),
    ForthState,
    evalText,
    toList,
    emptyState,
  )
where

import Control.Monad ( foldM )
import qualified Data.Map as M
import Data.Maybe ( fromMaybe )
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
  let texts = T.words $ T.toLower text
   in if head texts == ":"
        then
          let key_values = skipEnds texts
              key = head key_values
              values = tail key_values
              newValues = replaceSymbolValues values state
           in if isInteger key
                then Left (InvalidWord) 
                else Right $ insertSymbol key newValues state
        else foldM (flip fetchTextPart) state texts

-- Replace "symbolic values" from incoming symbols by real values
replaceSymbolValues :: [T.Text] -> ForthState -> [T.Text]
replaceSymbolValues values state =
  concatMap (replaceSymbolValue state) values

-- Replace "symbolic values" from incoming symbols by real values
replaceSymbolValue :: ForthState -> T.Text -> [T.Text]
replaceSymbolValue state value =
  if isInteger value
    then [value]
    else
      do
        Data.Maybe.fromMaybe [value] (getSymbol value state)

-- Insert into dictionary
insertSymbol :: T.Text -> [T.Text] -> ForthState -> ForthState
insertSymbol key values state =
  state {symbols = M.insert key values (symbols state)}

-- Read from dictionary
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
  | isInteger text = Right $ push (parseInt text) state
  | otherwise = Left (UnknownWord text)

dup :: ForthState -> Either ForthError ForthState
dup state =
  do
    (v, s1) <- pop state
    let s2 = push v s1
    let s3 = push v s2
    Right s3

swap :: ForthState -> Either ForthError ForthState
swap state =
  do
    (v1, s1) <- pop state
    (v2, s2) <- pop s1
    let s3 = push v1 s2
    let s4 = push v2 s3
    Right s4

over :: ForthState -> Either ForthError ForthState
over state =
  do
    (v1, s1) <- pop state
    (v2, s2) <- pop s1
    let s3 = push v2 s2
    let s4 = push v1 s3
    let s5 = push v2 s4
    Right s5

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
  case TR.decimal txt of
    Right (_, rest) -> T.null rest -- parsing gelukt en niets over
    Left _ -> False

parseInt :: T.Text -> Int
parseInt txt =
  case TR.decimal txt of
    Right (n, rest) | T.null rest -> n
    --    _                             -> Left "Geen geldige Int"
    _ -> 0

toList :: ForthState -> [Int]
toList state =
  let stackLijst = stackList state
   in reverse stackLijst

-- Stack helperfuncties

push :: Int -> ForthState -> ForthState
push x state = state {stack = x : stack state}

pop :: ForthState -> Either ForthError (Int, ForthState)
pop (ForthState [] _) = Left StackUnderflow
pop (ForthState (x : xs) symbols) = Right (x, ForthState xs symbols)

stackList :: ForthState -> [Int]
stackList (ForthState stack symbols) = stack
