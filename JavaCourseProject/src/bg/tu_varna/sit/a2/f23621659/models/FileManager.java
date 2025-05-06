package bg.tu_varna.sit.a2.f23621659.models;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_FOLDER = "database/";
    private static final String IMPORT_FOLDER = "imports/";
    private static final String CATALOG_FILE = "catalog.txt";

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
            directory.mkdirs();
        }

        File file = new File(path);

        if(file.exists()) {
            ErrorHandler.handleTableExistsError(fileName);
            return;
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

    public static void importTable(String fileName) {
        String importPath = IMPORT_FOLDER + fileName;
        String databasePath = DATA_FOLDER + fileName;
        String catalogPath = DATA_FOLDER + CATALOG_FILE;

        try {
            // Read file from imports/
            List<String> content = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(importPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.add(line);
                }
            }

            // Write file to database/
            FileManager.writeInFile(fileName, content);

            // Append table name to catalog.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(catalogPath, true))) {
                writer.write(fileName);
                writer.newLine();
            }

        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "importing table from " + importPath);
        }
    }

    public static void showTables() {
        String catalogPath = DATA_FOLDER + CATALOG_FILE;
        List<String> content = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(catalogPath))) {
            String line;

            while((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "reading " + catalogPath);
        }

        ConsoleWriter.printTables(content);
    }
}
