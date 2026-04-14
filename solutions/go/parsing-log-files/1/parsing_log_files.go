package parsinglogfiles

import (
	"regexp"
	"strings"
)

func IsValidLine(text string) bool {
	re := regexp.MustCompile(`^\[(TRC|DBG|INF|WRN|ERR|FTL)\].*`)
	return re.MatchString(text)
}

func SplitLogLine(text string) []string {
	re := regexp.MustCompile("<[~|*|=|-]*>")
	return re.Split(text, -1)
}

func CountQuotedPasswords(lines []string) int {
	re := regexp.MustCompile(`(?i)".*password.*"`)
	count := 0
	for _, line := range lines {
		indices := re.FindStringIndex(line)
		if len(indices) > 0 {
			count++
		}
	}
	return count
}

func RemoveEndOfLineText(text string) string {
	re := regexp.MustCompile(`end-of-line\d+`)
	return re.ReplaceAllString(text, "")
}

func TagWithUserName(lines []string) []string {
	re := regexp.MustCompile(`User[ ]+\w+ `)
	reSplit := regexp.MustCompile(`[ ]+`)

	output := []string{}

	for _, line := range lines {
		searchResult := re.FindString(line)
		if searchResult == "" {
			output = append(output, line)
		} else {
			searchResult = strings.TrimSpace(searchResult)
			wordsInSearchResult := reSplit.Split(searchResult, -1)
			updateLine := "[USR] " + wordsInSearchResult[1] + " " + line
			output = append(output, updateLine)
		}
	}
	return output
}
