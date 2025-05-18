package bg.tu_varna.sit.a2.f23621649.models;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {
    public static void handleFileNotFoundException(FileNotFoundException exception, String text) {
        System.err.println("Error: " + text);
        System.err.println("Details: " + exception.getMessage());
    }

    public static void handleIOException (IOException exception, String text) {
        System.err.println("Error: " + text);
        System.err.println("Details: " + exception.getMessage());
    }

    public static void handleTableExistsError(String tableName) {
        System.err.println("Error: A table with name " + tableName + " already exists");
    }

    public static void printException(String message) {
        System.err.println(message);
    }
}
