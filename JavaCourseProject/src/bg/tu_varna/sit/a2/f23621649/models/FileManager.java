package bg.tu_varna.sit.a2.f23621649.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static FileManager instance;
    private static final String DATA_FOLDER = "database/";
    private static final String IMPORT_FOLDER = "imports/";
    private static final String CATALOG_FILE = "catalog.txt";
    private final String catalogPath = DATA_FOLDER + CATALOG_FILE;

    private List<String> currentTableContent;
    private boolean isFileOpen;
    private String openedFileName;
    private boolean isModified;

    private FileManager()
    {
    }

    public static FileManager getInstance() {
        if(instance == null)
        {
            instance = new FileManager();
        }

        return  instance;
    }

    public List<String> readFile(String fileName)  {
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

    public boolean writeTableInFile(String fileName, List<String> content) {
        String path = DATA_FOLDER + fileName;


        File directory = new File(DATA_FOLDER);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(path);

        if(file.exists()) {
            ErrorHandler.handleTableExistsError(fileName);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
            for (String line:content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "writing in " + path);
        }

        return true;
    }

    public void writeInCatalogFile(String fileName, String content) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(catalogPath, true))) {
                writer.write(content);
                writer.newLine();
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "writing in " + fileName);
        }

    }

    public void updateTableInFile(String fileName, List<String> content) {
        String path = DATA_FOLDER + fileName;

        File directory = new File(DATA_FOLDER);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "updating " + path);
        }
    }

    public void importTable(String fileName) {
        String importPath = IMPORT_FOLDER + fileName;

        try {
            List<String> content = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(importPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.add(line);
                }
            }

            instance.writeTableInFile(fileName, content);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(catalogPath, true))) {
                writer.write(fileName);
                writer.newLine();
            }
            ConsoleWriter.printLine("Table imported successfully");


        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "importing table from " + importPath);
        }
    }

    public void showTables() {
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

        ConsoleWriter.printLines(content);
    }

    public void renameTable(String oldName, String newName) {
        String oldFileName = oldName + ".txt";
        String newFileName = newName + ".txt";

        File oldFile = new File(DATA_FOLDER, oldFileName);
        File newFile = new File(DATA_FOLDER, newFileName);

        if(!oldFile.exists()) {
            ErrorHandler.printException("Table doesn't exist");
            return;
        }

        if(newFile.exists()) {
            ErrorHandler.printException("Table with this new name already exists");
            return;
        }

        boolean success = oldFile.renameTo(newFile);

        if(success) {
            updateCatalogFile(oldFileName, newFileName);
            ConsoleWriter.printLine("Table " + oldName + " renamed to " + newName);
            ConsoleWriter.printNewLine();
        } else {
            ErrorHandler.printException("Table file has not been renamed");
            ConsoleWriter.printNewLine();
        }
    }

    public boolean validateOpenedFile(String fileName) {
        return isFileOpen && (openedFileName != null && openedFileName.equals(fileName));
    }

    public void openFile(String fileName) {
        if (isFileOpen) {
            ErrorHandler.printException("Another file is already open. Please close it first.");
            return;
        }

        File file = new File(DATA_FOLDER + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeInCatalogFile(catalogPath, fileName);
                ConsoleWriter.printLine("File did not exist, created new file: " + fileName);
            } catch (IOException ex) {
                ErrorHandler.handleIOException(ex, "creating new file " + fileName);
                return;
            }
        }

        List<String> content = readFile(fileName);
        this.currentTableContent = content;
        this.openedFileName = fileName;
        this.isFileOpen = true;
        this.isModified = false;

        ConsoleWriter.printLine("Successfully opened " + fileName);
    }

    public void closeFile() {
        if (!isFileOpen) {
            ErrorHandler.printException("No file is currently open.");
            return;
        }

        if (isModified) {
            ConsoleWriter.printLine("Warning: You have unsaved changes.");
        }

        currentTableContent = null;
        openedFileName = null;
        isFileOpen = false;
        isModified = false;

        ConsoleWriter.printLine("Successfully closed file.");
    }

    public void saveFile() {
        if (!isFileOpen) {
            ErrorHandler.printException("No file is currently open.");
            return;
        }

        updateTableInFile(openedFileName, currentTableContent);
        isModified = false;

        ConsoleWriter.printLine("Successfully saved " + openedFileName);
    }

    public void saveAsFile(String newFileName) {
        if (!isFileOpen) {
            ErrorHandler.printException("No file is currently open.");
            return;
        }

        boolean success = writeTableInFile(newFileName, currentTableContent);
        if (success) {
            writeInCatalogFile(CATALOG_FILE, newFileName);
            ConsoleWriter.printLine("Successfully saved " + newFileName);
        }
    }

    public boolean isFileOpen() {
        return isFileOpen;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public List<String> getCurrentTableContent() {
        return currentTableContent;
    }

    public void setCurrentTableContent(List<String> content) {
        this.currentTableContent = content;
        setModified(true);
    }

    public String getOpenedFileName() {
        return openedFileName;
    }

    private void updateCatalogFile(String oldFileName, String newFileName) {
        List<String> catalogEntries = readFile(CATALOG_FILE);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(catalogPath, false))) {

            for (String entry : catalogEntries) {

                if (entry.equals(oldFileName)) {
                    writer.write(newFileName);
                } else {
                    writer.write(entry);
                }

                writer.newLine();
            }
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "updating catalog during rename");
        }
    }
}
