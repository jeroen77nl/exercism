fn divisible(year: Int, n: Int) -> Bool {
  year % n == 0
}

pub fn is_leap_year(year: Int) -> Bool {
  divisible(year, 4) && { !divisible(year, 100) || divisible(year, 400) }
}
