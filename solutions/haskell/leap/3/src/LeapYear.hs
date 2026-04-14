module LeapYear (isLeapYear) where

isLeapYear :: Integer -> Bool
isLeapYear year =
  let yearDivBy d = year `mod` d == 0
   in yearDivBy 4 && (not (yearDivBy 100) || yearDivBy 400)