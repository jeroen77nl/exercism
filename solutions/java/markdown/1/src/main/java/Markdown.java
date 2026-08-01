class Markdown {

    private static final String REGEXP_BOLD = "__(.+)__";
    private static final String REGEXP_BOLD_UPDATER = "<strong>$1</strong>";
    private static final String REGEXP_ITALIC = "_(.+)_";
    private static final String REGEXP_ITALIC_UPDATER = "<em>$1</em>";

    private static final String MD_LIST_ITEM = "* ";

    private static final String HTML_P_OPEN = "<p>";
    private static final String HTML_P_CLOSE = "</p>";
    private static final String HTML_LI_OPEN = "<li>";
    private static final String HTML_LI_CLOSE = "</li>";
    private static final String HTML_UL_OPEN = "<ul>";
    private static final String HTML_UL_CLOSE = "</ul>";

    String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();
        boolean activeList = false;

        for (String inputLine : lines) {
            String parsedLine = parseLine(inputLine);

            if (parsedLine.startsWith(HTML_LI_OPEN) && !activeList) {
                activeList = true;
                result.append(HTML_UL_OPEN);
            } else if (!parsedLine.startsWith(HTML_LI_OPEN) && activeList) {
                activeList = false;
                result.append(HTML_UL_CLOSE);
            }
            result.append(parsedLine);
        }

        if (activeList) {
            // called when then last line in the input is a list item
            // otherwise activeList would have been set to false earlier on
            result.append(HTML_UL_CLOSE);
        }

        return result.toString();
    }

    private String parseLine(String inputLine) {

        if (inputLine.startsWith("#")) {
            return parseHeader(inputLine);
        }

        if (inputLine.startsWith(MD_LIST_ITEM)) {
            return parseListItem(inputLine);
        }

        return parseParagraph(inputLine);
    }

    private String parseHeader(String markdown) {
        int count = getHeaderLevel(markdown);

        if (count > 6) {
            return HTML_P_OPEN + markdown + HTML_P_CLOSE;
        }

        return "<h" + count + ">"
                + markdown.substring(count + 1)
                + "</h" + count + ">";
    }

    private static int getHeaderLevel(String markdown) {
        int count = 0;
        while (count < markdown.length() && markdown.charAt(count) == '#') {
            count++;
        }
        return count;
    }

    private String parseListItem(String markdown) {
        String listItemText =
                parseBoldAndItalic(
                        markdown.substring(MD_LIST_ITEM.length()));
        return HTML_LI_OPEN + listItemText + HTML_LI_CLOSE;
   }

    private String parseParagraph(String markdown) {
        return HTML_P_OPEN + parseBoldAndItalic(markdown) + HTML_P_CLOSE;
    }

    private String parseBoldAndItalic(String markdown) {
        return parseItalic(parseBold(markdown));
    }

    private String parseBold(String markdown) {
        return markdown.replaceAll(REGEXP_BOLD, REGEXP_BOLD_UPDATER);
    }

    private String parseItalic(String markdown) {
        return markdown.replaceAll(REGEXP_ITALIC, REGEXP_ITALIC_UPDATER);

    }
}
