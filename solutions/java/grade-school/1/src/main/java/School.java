import java.util.*;

class School {

    private static final Comparator<Map.Entry<String, Integer>>
            BY_GRADE_THEN_NAME =
            Map.Entry.<String, Integer>comparingByValue()
                    .thenComparing(Map.Entry::getKey);

    private static final Comparator<Map.Entry<String, Integer>>
            BY_NAME =
            Map.Entry.comparingByKey();

    private final Map<String, Integer> school = new HashMap<String, Integer>();

    boolean add(String student, int grade) {
        if (school.containsKey(student)) {
            return false;
        }

        school.put(student, grade);
        return true;

    }

    List<String> roster() {
        return school.entrySet().stream()
                .sorted(BY_GRADE_THEN_NAME)
                .map(Map.Entry::getKey)
                .toList();
    }

    List<String> grade(int grade) {
        return school.entrySet().stream()
                .filter(entry -> entry.getValue() == grade)
                .sorted(BY_NAME)
                .map(Map.Entry::getKey)
                .toList();
    }
}
