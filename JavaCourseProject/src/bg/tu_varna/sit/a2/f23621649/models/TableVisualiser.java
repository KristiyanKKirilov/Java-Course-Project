package bg.tu_varna.sit.a2.f23621649.models;

import java.util.Scanner;

public class TableVisualiser {
    public void display(Pagination pagination) {
        Scanner scanner = new Scanner(System.in);
        pagination.showCurrentPage();
        boolean running = true;

        while (running) {
            ConsoleWriter.printLine("Enter command: next | previous | exit");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "next" -> {
                    if (pagination.hasNext()) {
                        pagination.nextPage();
                        pagination.showCurrentPage();
                    } else {
                        ConsoleWriter.printLine("Next page does not exist");
                    }
                }
                case "previous" -> {
                    if (pagination.hasPrevious()) {
                        pagination.previousPage();
                        pagination.showCurrentPage();
                    } else {
                        ConsoleWriter.printLine("Previous page does not exist");
                    }
                }
                case "exit" -> {
                    running = false;
                    ConsoleWriter.printLine("Exited from table view");
                }
                default -> ConsoleWriter.printLine("Invalid command");
            }
        }

    }
}
