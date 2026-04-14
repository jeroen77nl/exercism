import java.util.ArrayList;
import java.util.List;

public class LanguageList {
    private final List<String> languages = new ArrayList<>();

    public boolean isEmpty() {
        return languages.isEmpty();
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

    public void removeLanguage(String language) {
        languages.removeIf(s -> s.equals(language));
    }

    public String firstLanguage() {
        return languages.getFirst();
    }

    public int count() {
        return languages.size();
    }

    public boolean containsLanguage(String language) {
        return languages.contains(language);
    }

    public boolean isExciting() {
        List<String> exciting = List.of("Java", "Kotlin");
        return languages.stream().anyMatch(exciting::contains);
    }
}
