public class Twofer {
    public String twofer(String name) {
        String newName = name == null ? "you" : name;
        return "One for " + newName + ", one for me.";
    }
}
