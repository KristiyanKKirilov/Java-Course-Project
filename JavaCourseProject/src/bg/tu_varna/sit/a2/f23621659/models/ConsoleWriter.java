package bg.tu_varna.sit.a2.f23621659.models;

import java.util.List;

public class ConsoleWriter {
    public static void printTables(List<String> content) {
        for(String line: content) {
            System.out.println(line);
        }
    }

    public static void printDescription(String description) {
        System.out.println(description);
    }
}
