package se.slackers.stml.yaml;

import se.slackers.stml.model.TypeClass;

import java.util.Objects;

public class Output {
    private static final int LINE_LENGTH = 120;
    private StringBuilder output = new StringBuilder();

    public String toString() {
        return output.toString();
    }

    public void newStructure() {
        output.append("---\n");
    }

    public void write(String string) {
        output.append(string);
    }

    public void writeValue(String indentation, Object content, TypeClass typeClass) {
        writeValue(indentation, Objects.toString(content), typeClass);
    }

    public void writeValue(String indentation, String content, TypeClass typeClass) {
        if (content.contains("\n")) {
            output.append("|-");
            for (String line : content.split("\n")) {
                output.append("\n");
                output.append(indentation);
                output.append(line);
            }
            return;
        }
        if (content.contains("\\n")) {
            output.append("|-");
            for (String line : content.split("\\\\n")) {
                output.append("\n");
                output.append(indentation);
                output.append(line);
            }
            return;
        }

        if (indentation.length() + content.length() > LINE_LENGTH) {
            output.append(">");
            if (content.contains(" ")) {
                int length = content.length();
                int offset = 0;
                int chunk = LINE_LENGTH - indentation.length();
                while (offset < length) {
                    // search for EOF or whitespace
                    int end = Math.min(offset + chunk - 5, length);
                    while (end < length && content.charAt(end) != ' ') {
                        end++;
                    }
                    end = Math.min(end + 1, length);

                    String line = content.substring(offset, end);
                    offset = end;

                    output.append("\n");
                    output.append(indentation);
                    output.append(line);
                }
                return;
            }
        }

        if (typeClass == TypeClass.STRING && needsEscape(content)) {
            output.append('"').append(escape(content)).append('"');
            return;
        }

        output.append(content);
    }

    private boolean needsEscape(String content) {
        if (content.length() == 0) {
            return true;
        }
        switch (content.charAt(0)) {
            case '!':
            case '&':
            case '*':
            case '-':
            case ':':
            case '?':
            case '#':
            case '|':
            case '>':
            case '@':
            case '`':
            case '"':
            case '\'':
                return true;
        }
        if (content.matches("[0-9]+")) {
            return true;
        }
        if (content.contains(": ") ||
                content.contains(" #") ||
                content.contains(",") ||
                content.contains("[") ||
                content.contains("]") ||
                content.contains("{") ||
                content.contains("}")) {
            return true;
        }
        return false;
    }

    private String escape(String line) {
        return line.replace("\\", "\\\\")
                .replace("\n", "\\n");
    }

    public void writeKey(String key) {
        output.append(key).append(": ");
    }

    public void newLine() {
        output.append('\n');
    }

    public void writeListItem() {
        output.append("- ");
    }

    public boolean endsWithNewLine() {
        if (output.length() > 0) {
            return '\n' == output.charAt(output.length()-1);
        }
        return true;
    }
}
