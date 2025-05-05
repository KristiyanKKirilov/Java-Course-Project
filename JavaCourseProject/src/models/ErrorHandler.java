package models;

import java.io.IOException;

public class ErrorHandler {
    public static void handleIOException (IOException exception, String text) {
        System.err.println("ERROR: " + text);
        System.err.println("Details: " + exception.getMessage());
    }
}
