import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;

class RelativeDistance {

    static record PersonState(String person, int distance) {
    }

    private final Map<String, Set<String>> familyGraph;

    RelativeDistance(Map<String, List<String>> familyTree) {
        this.familyGraph = makeGraph(familyTree);
    }

    private static Map<String, Set<String>> makeGraph(Map<String, List<String>> familyTree) {

        Map<String, Set<String>> graph = new HashMap<>();

        for (var entry : familyTree.entrySet()) {
            String parent = entry.getKey();
            List<String> children = entry.getValue();

            // parent -> children
            Set<String> parentNeighbors = graph.computeIfAbsent(parent, k -> new HashSet<>());
            parentNeighbors.addAll(children);

            // child -> parent
            for (String child : children) {
                Set<String> childNeighbors = graph.computeIfAbsent(child, k -> new HashSet<>());
                childNeighbors.add(parent);
                childNeighbors.addAll(children);
                childNeighbors.remove(child);
            }
        }
        return graph;
    }

    int degreeOfSeparation(String personA, String personB) {
        if (personA.equals(personB)) {
            return 0;
        }

        Queue<PersonState> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new PersonState(personA, 0));
        visited.add(personA);

        while (!queue.isEmpty()) {
            PersonState current = queue.poll();

            if (current.person.equals(personB)) {
                return current.distance;
            }

            for (String neighbor : familyGraph.getOrDefault(current.person, Collections.emptySet())) {
                if (visited.add(neighbor)) {
                    queue.offer(new PersonState(neighbor, current.distance + 1));
                }
            }
        }

        return -1;
    }
}
