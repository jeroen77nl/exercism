import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

class RelativeDistance {

    static class State {
        String node;
        int distance;

        State(String node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    private final Map<String, Set<String>> familyGraph;

    RelativeDistance(Map<String, List<String>> familyTree) {
        this.familyGraph = makeGraph(familyTree);
    }

    Map<String, Set<String>> makeGraph(Map<String, List<String>> familyTree) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (Entry<String, List<String>> entry : familyTree.entrySet()) {
            String parent = entry.getKey();
            List<String> children = entry.getValue();
            // parent -> children
            Set<String> set = graph.getOrDefault(parent, new HashSet<>());
            for (String child : children) {
                set.add(child);
            }
            graph.put(parent, set);
            // child -> parent
            for (String child :children) {
                Set<String> set2 = graph.getOrDefault(child, new HashSet<>());
                set2.add(parent);
                graph.put(child, set2);
            }
            // child -> child
            if (children.size() == 2) {
                String child1 = children.getFirst();
                String child2 = children.getLast();

                Set<String> set3 = graph.getOrDefault(child1, new HashSet<>());
                set3.add(child2);
                graph.put(child1, set3);

                Set<String> set4 = graph.getOrDefault(child2, new HashSet<>());
                set4.add(child1);
                graph.put(child2, set4);
            }
        }
        return graph;
    }

    int degreeOfSeparation(String personA, String personB) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new State(personA, 0));
        visited.add(personA);

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.node.equals(personB)) {
                return current.distance;
            }

            for (String neighbor : familyGraph.getOrDefault(current.node, Collections.emptySet())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(new State(neighbor, current.distance + 1));
                }
            }
        }

        return -1;
    }
}
