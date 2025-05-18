package bg.tu_varna.sit.a2.f23621649.models;

import java.util.List;

public class ConsoleWriter {
    public static void printLines(List<String> content) {
        for(String line: content) {
            System.out.println(line);
        }
    }

    public static void printLine(String description) {
        System.out.println(description);
    }

    public static void printNewLine() {
        System.out.println();
    }

}
