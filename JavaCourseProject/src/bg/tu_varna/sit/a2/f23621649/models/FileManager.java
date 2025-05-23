package bg.tu_varna.sit.a2.f23621649.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класът FileManager отговаря за управление на файлове и таблици в директорията на базата от данни.
 * Реализира Singleton модел.
 */
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

    /**
     * Връща инстанцията на FileManager.
     *
     * @return инстанцията (Singleton)
     */
    public static FileManager getInstance() {
        if(instance == null)
        {
            instance = new FileManager();
        }

        return  instance;
    }

    /**
     * Чете файл от папката database.
     *
     * @param fileName Името на файла
     * @return Списък със съдържанието на файла
     */
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

    /**
     * Записва таблица във файл, ако файлът не съществува.
     *
     * @param fileName Име на файла
     * @param content Съдържание за запис
     * @return true ако успешно, иначе false
     */
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

    /**
     * Добавя запис във файла catalog.txt.
     *
     * @param fileName Името на файла (за логове)
     * @param content Съдържание за добавяне
     */
    public void writeInCatalogFile(String fileName, String content) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(catalogPath, true))) {
                writer.write(content);
                writer.newLine();
        } catch (IOException ex) {
            ErrorHandler.handleIOException(ex, "writing in " + fileName);
        }

    }

    /**
     * Обновява съдържанието на таблица във файл.
     *
     * @param fileName Името на файла
     * @param content Новото съдържание
     */
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

    /**
     * Вмъква таблица от папка imports и я добавя в catalog.txt.
     *
     * @param fileName Името на файла за импортиране
     */
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

    /**
     * Извежда всички таблици, записани в catalog.txt.
     */
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

    /**
     * Преименува таблица и обновява catalog.txt.
     *
     * @param oldName Старото име (без разширение)
     * @param newName Новото име (без разширение)
     */
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

    /**
     * Проверява дали даден файл е текущо отвореният.
     *
     * @param fileName Името на файла
     * @return true ако е отворен и съвпада, иначе false
     */
    public boolean validateOpenedFile(String fileName) {
        return isFileOpen && (openedFileName != null && openedFileName.equals(fileName));
    }

    /**
     * Отваря файл. Ако не съществува, го създава.
     *
     * @param fileName Името на файла
     */
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

    /**
     * Затваря текущия файл.
     */
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

    /**
     * Записва текущите промени във файла.
     */
    public void saveFile() {
        if (!isFileOpen) {
            ErrorHandler.printException("No file is currently open.");
            return;
        }

        updateTableInFile(openedFileName, currentTableContent);
        isModified = false;

        ConsoleWriter.printLine("Successfully saved " + openedFileName);
    }

    /**
     * Записва текущото съдържание в нов файл.
     *
     * @param newFileName Новото име на файла
     */
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

    /**
     * Задава състоянието на модификация на текущия файл.
     *
     * @param modified true ако има промени
     */
    public void setModified(boolean modified) {
        isModified = modified;
    }


    /**
     * Задава ново съдържание и маркира файла като модифициран.
     *
     * @param content новото съдържание
     */
    public void setCurrentTableContent(List<String> content) {
        this.currentTableContent = content;
        setModified(true);
    }


    /**
     * Обновява каталожния файл след преименуване на таблица.
     *
     * @param oldFileName Старото име
     * @param newFileName Новото име
     */
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
