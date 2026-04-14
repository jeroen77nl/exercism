pub fn convert(number: Int) -> String {
    case number {
        i if i >= 1000 -> "M" <> convert(i - 1000)
        i if i >= 900 -> "CM" <> convert(i - 900)
        i if i >= 500 -> "D" <> convert(i - 500)
        i if i >= 400 -> "CD" <> convert(i - 400)
        i if i >= 100 -> "C" <> convert(i - 100)
        i if i >= 90 -> "XC" <> convert(i - 90)
        i if i >= 50 -> "L" <> convert(i - 50)
        i if i >= 40 -> "XL" <> convert(i - 40)
        i if i >= 10 -> "X" <> convert(i - 10)
        i if i >= 9 -> "IX" <> convert(i - 9)
        i if i >= 5 -> "V" <> convert(i - 5)
        i if i >= 4 -> "IV" <> convert(i - 4)
        i if i >= 1 -> "I" <> convert(i - 1)
        _ -> ""
    }
}
