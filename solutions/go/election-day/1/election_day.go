package electionday

import "fmt"

func NewVoteCounter(initialVotes int) *int {
	return &initialVotes
}

func VoteCount(counter *int) int {
	if counter == nil {
		return 0
	} else {
		return *counter
	}
}

func IncrementVoteCount(counter *int, increment int) {
	*counter += increment
}

func NewElectionResult(candidateName string, votes int) *ElectionResult {
	return &ElectionResult{
		Name: candidateName,
		Votes: votes,
	}
}

func DisplayResult(result *ElectionResult) string {
	return fmt.Sprintf("%v (%v)", result.Name, result.Votes)
}

// DecrementVotesOfCandidate decrements by one the vote count of a candidate in a map.
func DecrementVotesOfCandidate(results map[string]int, candidate string) {
	results[candidate] -= 1
}
