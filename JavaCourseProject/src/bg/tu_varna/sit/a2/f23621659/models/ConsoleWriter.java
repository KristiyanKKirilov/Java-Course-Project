package bg.tu_varna.sit.a2.f23621659.models;

import java.util.List;

public class ConsoleWriter {
    public static void print(List<String> content) {
        for(String line: content) {
            System.out.println(line);
        }
    }
}
