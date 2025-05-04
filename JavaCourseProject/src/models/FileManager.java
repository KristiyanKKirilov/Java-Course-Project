package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<String>  readFile(String path) throws IOException {
        List<String> lines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader((new FileReader(path)));
            String line;
            while((line = reader.readLine()) != null) {
                    lines.add(line);
            }
        } catch(IOException ex)
        {

        }

        return lines;
    }

    public static void writeFile(String path, List<String> content) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            for (String line:content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {

        }
    }
}
