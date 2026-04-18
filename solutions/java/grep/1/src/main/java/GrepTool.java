import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class GrepTool {

    private boolean numbered;               // -n
    private boolean filenamesOnly;          // -l
    private boolean caseInsensitive;        // -i
    private boolean invertedMatch;          // -v
    private boolean matchWholeLines;        // -x

    private List<String> files;

    String grep(String pattern, List<String> flags, List<String> files) {
        parseFlags(flags);
        this.files = files;

        List<String> selectedLines = new ArrayList<>();
        List<String> selectedFileNames = new ArrayList<>();
        for (String fileName : files) {
            boolean linesInFileSelected = false;
            try {
                List<String> allLines = Files.readAllLines(Paths.get(fileName));

                for (int i = 0; i < allLines.size(); i++) {
                    if (matchLine(allLines.get(i), pattern)) {
                        linesInFileSelected = true;
                        String line = allLines.get(i);
                        if (numbered) {
                            line = i + 1 + ":" + line;
                        }
                        if (files.size() > 1) {
                            line = fileName + ":" + line;
                        }
                        selectedLines.add(line);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (linesInFileSelected) {
                selectedFileNames.add(fileName);
            }
        }
        if (filenamesOnly) {
            return String.join("\n", selectedFileNames);
        } else {
            return String.join("\n", selectedLines);
        }
    }

    private boolean matchLine(String line, String pattern) {
        if (caseInsensitive) {
            pattern = pattern.toLowerCase();
            line = line.toLowerCase();
        }
        boolean matches = false;
        if (matchWholeLines) {
            if (line.equals(pattern)) {
                matches = true;
            }
        }else{
            if (line.contains(pattern)) {
                matches = true;
            }
        }
        if (invertedMatch) {
            matches = !matches;
        }
        return matches;
    }

    private void parseFlags(List<String> flags) {
        numbered = false;
        filenamesOnly = false;
        caseInsensitive = false;
        invertedMatch = false;
        matchWholeLines = false;

        for (String flag : flags) {
            switch (flag) {
                case "-n":
                    numbered = true;
                    break;
                case "-l":
                    filenamesOnly = true;
                    break;
                case "-i":
                    caseInsensitive = true;
                    break;
                case "-v":
                    invertedMatch = true;
                    break;
                case "-x":
                    matchWholeLines = true;
            }
        }
    }

}