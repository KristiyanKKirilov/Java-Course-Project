package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.enums.DataType;
import bg.tu_varna.sit.a2.f23621659.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = FileManager.getInstance();

//                    List<String> headers = Arrays.asList("ID", "Name");
//                    List<String> types = Arrays.asList("int", "string");
//
//                    Table jobPositionsTable = new Table(headers, types);
//                    jobPositionsTable.addRow(Arrays.asList("1", "CEO"));
//                    jobPositionsTable.addRow(Arrays.asList("2", "Manager"));
//                    jobPositionsTable.addRow(Arrays.asList("3", "Worker"));
//                    jobPositionsTable.addRow(Arrays.asList("4", "Trainer"));

        String input;
        while (!(input = scanner.nextLine()).equals("END")) {
            String[] command = input.split("-");

            switch (command[0]) {
                case "import": {
                    String fileName = command[1];
                    fileManager.importTable(fileName);
                    ConsoleWriter.printDescription("Table imported successfully.");
                    break;
                }
                case "showtables":
                    fileManager.showTables();
                    break;
                case "describe": {
                    String tableName = command[1];
                    String fileName = tableName + ".txt";

                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);
                    String description = table.getColumnDescription();
                    ConsoleWriter.printDescription(description);
                }
                break;
                case "print": {
                    String tableName = command[1];
                    String fileName = tableName + ".txt";

                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);
                    TableDialog.showTableInDialogWithPagination(table, 5);
                }
                break;
                case "export": {
                    String tableName = command[1];
                    String fileName = command[2];
                    String tableFileName = tableName + ".txt";

                    List<String> tableData = fileManager.readFile(tableFileName);
                    fileManager.writeTableInFile(fileName, tableData);
                    fileManager.writeInCatalogFile("catalog.txt", fileName);

                    ConsoleWriter.printDescription("Table " + tableName + " exported in file " + fileName + " successfully.");
                }
                break;
                case "select": {
                    try {
                        int columnIndex = Integer.parseInt(command[1]);
                        String value = command[2];
                        String fileName = command[3];

                        List<String> tableData = fileManager.readFile(fileName);
                        Table table = TableCreator.createTable(tableData);

                        Table filteredTable = table.selectRowsByColumnValue(columnIndex, value);

                        TableDialog.showTableInDialogWithPagination(filteredTable, 5);
                    } catch (Exception e) {
                        System.out.println("Error: Invalid input or column index. Usage: select <column-index> <value> <file>");
                    }
                }
                break;
                case "addcolumn": {
                    String tableName = command[1];
                    String columnName = command[2];
                    String fileName = tableName + ".txt";

                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);

                    try {
                        DataType columnType = DataType.fromString(command[3]);
                        table.addColumn(columnName, columnType);

                        List<String> updatedTableData = table.getTableData();
                        fileManager.updateTableInFile(fileName, updatedTableData);

                        ConsoleWriter.printDescription("Column added successfully.");
                    } catch (IllegalArgumentException ex) {
                        ErrorHandler.printException("Invalid column type. Allowed types: INT, DOUBLE, STRING");
                    }
                }
                break;
                case "update": {
                    String tableName = command[1];
                    int searchColumnIndex = Integer.parseInt(command[2]);
                    String searchValue = command[3];
                    int targetColumnIndex = Integer.parseInt(command[4]);
                    String targetValue = command[5];
                    String fileName = tableName + ".txt";

                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);

                    try {
                        table.updateRowsByColumnValue(searchColumnIndex, searchValue, targetColumnIndex, targetValue);
                        List<String> updatedTableData = table.getTableData();
                        fileManager.updateTableInFile(fileName, updatedTableData);

                        ConsoleWriter.printDescription("Row updated successfully.");
                    } catch (IllegalArgumentException ex) {
                        ErrorHandler.printException(ex.getMessage());
                    }
                }
                break;
                case "delete": {
                    String tableName = command[1];
                    String fileName = tableName + ".txt";
                    int searchColumnIndex = Integer.parseInt(command[2]);
                    String searchValue = command[3];

                    List<String> tableData = fileManager.readFile(fileName);
                    Table table = TableCreator.createTable(tableData);

                    try {
                        table.deleteRowsByColumnValue(searchColumnIndex, searchValue);
                        List<String> updatedTableData = table.getTableData();
                        fileManager.updateTableInFile(fileName, updatedTableData);

                        ConsoleWriter.printDescription("Row deleted successfully.");
                    } catch (IllegalArgumentException ex) {
                        ErrorHandler.printException(ex.getMessage());
                    }
                }
                break;
                case "insert": {
                    if (command.length < 3) {
                        ErrorHandler.printException("Not enough arguments passed");
                        break;
                    }

                    String tableName = command[1];
                    String fileName = tableName + ".txt";

                    try {
                        List<String> tableData = fileManager.readFile(fileName);
                        Table table = TableCreator.createTable(tableData);

                        List<String> values = Arrays.asList(command).subList(2, command.length);
                        table.addRow(values);

                        List<String> updatedTableData = table.getTableData();
                        fileManager.updateTableInFile(fileName, updatedTableData);

                        ConsoleWriter.printDescription("Row inserted successfully");
                    } catch (IllegalArgumentException ex) {
                        ErrorHandler.printException(ex.getMessage());
                    }
                }
                break;
                default:
                    break;
            }
        }

    }
}