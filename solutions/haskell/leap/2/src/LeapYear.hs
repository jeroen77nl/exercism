module LeapYear (isLeapYear) where

isLeapYear :: Integer -> Bool
isLeapYear year =
  yearDivBy 4 && (not (yearDivBy 100) || yearDivBy 400)
  where
    yearDivBy d = year `mod` d == 0