class BottleSong {

    String[] numbersWords = {
            "No", "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten"
    };

    String recite(int startBottles, int takeDown) {
        String[] verses = new String[takeDown];

        for (int i = 0; i < takeDown; i++) {
            verses[i] = verse(startBottles - i);
        }

        return String.join("\n", verses);
    }

    private String verse(int n) {
        String lineOneAndTwo = "%s green %s hanging on the wall,\n"
                .formatted(numbersWords[n], bottles(n));
        String lineThree = "And if one green bottle should accidentally fall,\n";
        String lineFour = "There'll be %s green %s hanging on the wall.\n"
                .formatted(numbersWords[n - 1].toLowerCase(), bottles(n - 1));
        return lineOneAndTwo + lineOneAndTwo + lineThree + lineFour;
    }

    private String bottles(int number) {
        return number == 1
                ? "bottle"
                : "bottles";
    }
}