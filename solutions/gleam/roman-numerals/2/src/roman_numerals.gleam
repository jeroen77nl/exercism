pub fn convert(number: Int) -> String {
    convert_iter("", number)
}

fn convert_iter(result: String, number: Int) -> String {
    case number {
        i if i >= 1000 -> convert_iter(result <> "M",i - 1000)
        i if i >= 900 -> convert_iter(result <> "CM",i - 900)
        i if i >= 500 -> convert_iter(result <> "D",i - 500)
        i if i >= 400 -> convert_iter(result <> "CD",i - 400)
        i if i >= 100 ->  convert_iter(result <> "C",i - 100)
        i if i >= 90 -> convert_iter(result <> "XC",i - 90)
        i if i >= 50 ->  convert_iter(result <> "L",i - 50)
        i if i >= 40 ->  convert_iter(result <> "XL",i - 40)
        i if i >= 10 ->  convert_iter(result <> "X",i - 10)
        i if i >= 9 ->  convert_iter(result <> "IX",i - 9)
        i if i >= 5 -> convert_iter(result <> "V",i - 5)
        i if i >= 4 ->  convert_iter(result <> "IV",i - 4)
        i if i >= 1 -> convert_iter(result <> "I",i - 1)
        _ -> result
    }
}
