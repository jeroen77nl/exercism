package blackjack

func ParseCard(card string) int {
	var val int
	switch card {
	case "ace":
		val = 11
	case "king":
		val = 10
	case "queen":
		val = 10
	case "jack":
		val = 10
	case "ten":
		val = 10
	case "nine":
		val = 9
	case "eight":
		val = 8
	case "seven":
		val = 7
	case "six":
		val = 6
	case "five":
		val = 5
	case "four":
		val = 4
	case "three":
		val = 3
	case "two":
		val = 2
	default:
		val = 0
	}
	return val
}

func FirstTurn(card1, card2, dealerCard string) string {
	sum := ParseCard(card1) + ParseCard(card2)
	dealerCardValue := ParseCard(dealerCard)

	switch {
	case card1 == "ace" && card2 == "ace":
		return "P"
	case sum == 21:
		if dealerCard != "ace" && dealerCardValue != 10 {
			return "W"
		} else {
			return "S"
		}
	case sum >= 17:
		return "S"
	case sum >= 12:
		if dealerCardValue >= 7 {
			return "H"
		} else {
			return "S"
		}
	default:
		return "H"
	}
}
