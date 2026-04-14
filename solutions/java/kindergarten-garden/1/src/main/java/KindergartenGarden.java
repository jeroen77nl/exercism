import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KindergartenGarden {

    private static final String[] studentNames = {
            "Alice", "Bob", "Charlie", "David", "Eve", "Fred",
            "Ginny", "Harriet", "Ileana", "Joseph", "Kincaid", "Larry"
    };

    private final Map<String, List<Plant>> plantsOfStudents;

    KindergartenGarden(String garden) {
        this.plantsOfStudents = parseGardenInput(garden);
    }

    private Map<String, List<Plant>> parseGardenInput(String garden) {
        String[] rows = garden.split("\\R");

        if (rows.length != 2 || rows[0].length() != rows[1].length() || rows[0].length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid garden input");
        }

        String row1 = rows[0];
        String row2 = rows[1];

        int studentCount = row1.length() / 2;

        if (studentCount > studentNames.length) {
            throw new IllegalArgumentException("Too many students for predefined list");
        }

        Map<String, List<Plant>> result = new HashMap<>();

        for (int i = 0; i < studentCount; i++) {
            int col = i * 2;

            result.put(
                    studentNames[i],
                    List.of(
                            Plant.getPlant(row1.charAt(col)),
                            Plant.getPlant(row1.charAt(col + 1)),
                            Plant.getPlant(row2.charAt(col)),
                            Plant.getPlant(row2.charAt(col + 1))
                    )
            );
        }

        return Map.copyOf(result);
    }

    List<Plant> getPlantsOfStudent(String student) {
        List<Plant> plants = plantsOfStudents.get(student);
        if (plants == null) {
            throw new IllegalArgumentException("Unknown student: " + student);
        }
        return plants;
    }
}