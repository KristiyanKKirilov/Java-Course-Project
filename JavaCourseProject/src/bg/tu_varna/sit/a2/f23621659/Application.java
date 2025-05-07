package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = FileManager.getInstance();


        String input;
        while (!(input = scanner.nextLine()).equals("END")) {
            String[] command = input.split(" ");

            switch (command[0]) {
                case "import": {
                    String fileName = command[1];
                    fileManager.importTable(fileName);
                    break;
                }
                case "showtables":
                    FileManager.showTables();
                    break;
                case "describe": {
                    String fileName = command[1];
                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);
                    String description = table.getColumnDescription();
                    ConsoleWriter.printDescription(description);
                }
                break;
                case "print": {
                    String fileName = command[1];
                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);
                    TableDialog.showTableInDialogWithPagination(table, 5);
                }
                case "export": {
                    List<String> headers = Arrays.asList("ID", "Name");
                    List<String> types = Arrays.asList("int", "string");

                    Table jobsTable = new Table(headers, types);
                    jobsTable.addRow(Arrays.asList("1", "Driver"));
                    jobsTable.addRow(Arrays.asList("2", "Construction worker"));
                    jobsTable.addRow(Arrays.asList("3", "F1 Racer"));
                    jobsTable.addRow(Arrays.asList("4", "Cook Chef"));
                    jobsTable.addRow(Arrays.asList("5", "Zoo keeper"));

                    List<String> seasonsTableData = jobsTable.getTableData();
                    fileManager.writeTableInFile("jobs.txt", seasonsTableData);
                }
                break;
                default:
                    break;
            }
        }

    }
}