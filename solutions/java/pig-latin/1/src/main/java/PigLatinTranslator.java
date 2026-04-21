class PigLatinTranslator {
    public String translate(String sentence) {
        String[] words = sentence.split(" ");
        String[] result = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            result[i] = translateWord(words[i]);
        }

        return String.join(" ", result);
    }

    private String translateWord(String word) {
        word = word.toLowerCase();

        // rule 1
        if (isVowel(word.charAt(0))
                || word.startsWith("xr") || word.startsWith("yt")) {
            return word + "ay";
        }

        int iConsPrefix = lastIndexOfBeginningConsonants(word);

        // rule 3
        int iQu = word.indexOf("qu");
        if (iQu == 0) {
            return word.substring(2) + "quay";
        }
        if (iQu >= 0 && iQu <= iConsPrefix + 1) {
            return word.substring(iQu + 2)
                    + word.substring(0, iQu)
                    + "qu"
                    + "ay";
        }

        // rule 4
        int iPosY = word.indexOf('y');
        if (iPosY >= 1 && iConsPrefix + 1 >= iPosY) {
            return word.substring(iPosY)
                    + word.substring(0, iPosY)
                    + "ay";
        }

        // rule 2
        if (iConsPrefix >= 0) {
            return word.substring(iConsPrefix + 1)
                    + word.substring(0, iConsPrefix + 1)
                    + "ay";
        }

        return word;
    }

    private int lastIndexOfBeginningConsonants(String s) {
        int i = -1;
        for (int j = 0; j < s.length(); j++) {
            if (isConsonant(s.charAt(j))) {
                i = j;
            } else {
                return i;
            }
        }
        return i;
    }

    private boolean isVowel(char c) {
        return "aeiou".indexOf(c) >= 0;
    }

    private boolean isConsonant(char c) {
        return isLetter(c) && !isVowel(c);
    }

    private boolean isLetter(char c) {
        return c >= 'a' && c <= 'z';
    }
}