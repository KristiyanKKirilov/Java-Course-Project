package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_FOLDER = "data/";

    public static List<String>  readFile(String fileName)  {
        List<String> lines = new ArrayList<>();
        String path = DATA_FOLDER + fileName;

        try (BufferedReader reader = new BufferedReader((new FileReader(path)))){

            String line;
            while((line = reader.readLine()) != null) {
                    lines.add(line);
            }
        } catch(IOException ex)
        {
            ErrorHandler.handleIOException(ex, "reading " + path);
        }

        return lines;
    }

    public static void writeInFile(String fileName, List<String> content) {
        String path = DATA_FOLDER + fileName;


            File directory = new File(DATA_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs(); // Creates the folder if it doesn't exist
            }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){


            for (String line:content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "writing in " + path);
        }
    }
}
