export function dayRate(ratePerHour) {
  return ratePerHour * 8;
}

export function daysInBudget(budget, ratePerHour) {
  return Math.floor(budget / dayRate(ratePerHour));
}

export function priceWithMonthlyDiscount(ratePerHour, numDays, discount) {
  let ratePerDay = dayRate(ratePerHour);

  let wholeMonths = Math.floor(numDays / 22);
  let rateForWholeMonths = wholeMonths * 22 * ratePerDay * (1 - discount);
  
  let remainingDays = numDays % 22;
  let rateForRemainingDays = remainingDays * ratePerDay;
  
  return Math.ceil(rateForWholeMonths + rateForRemainingDays);
}
