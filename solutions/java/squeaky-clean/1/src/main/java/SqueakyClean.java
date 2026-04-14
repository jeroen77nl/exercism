import java.util.Map;

class SqueakyClean {

    static String replaceSpaces(String s) {
        return s.replace(' ', '_');
    }

    static String toCamelCase(String s) {
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == '-') {
                a[i + 1] = Character.toUpperCase(a[i + 1]);
            }
        }
        return new String(a);
    }

    static String leetSpeak(String s) {
        Map<Character, Character> tabel = Map.of(
                '4', 'a',
                '3', 'e',
                '0', 'o',
                '1', 'l',
                '7', 't');

        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = tabel.getOrDefault(a[i], a[i]);
        }
        return new String(a);
    }

    static String removeOthers(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)) || s.charAt(i) == '_') {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    static String clean(String identifier) {
        String s = replaceSpaces(identifier);
        s = toCamelCase(s);
        s = leetSpeak(s);
        s = removeOthers(s);

        return s;
    }
}