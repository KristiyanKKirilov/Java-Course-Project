package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.enums.AggregateOperation;
import bg.tu_varna.sit.a2.f23621659.enums.DataType;
import bg.tu_varna.sit.a2.f23621659.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = FileManager.getInstance();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("end")) {
                System.exit(0);
            }

            String command;
            List<String> arguments;

            int spaceIndex = input.indexOf(" ");

            if (spaceIndex != -1) {
                command = input.substring(0, spaceIndex).toLowerCase();
                arguments = extractArguments(input.substring(spaceIndex));
            } else {
                command = input.toLowerCase();
                arguments = new ArrayList<>();
            }

            try {
                switch (command) {
                    case "import": {
                        String tableName = arguments.get(0);
                        String fileName = tableName + ".txt";
                        fileManager.importTable(fileName);
                        ConsoleWriter.printDescription("Table imported successfully.");
                        break;
                    }
                    case "showtables":
                        fileManager.showTables();
                        break;
                    case "describe": {
                        String tableName = arguments.get(0);
                        String fileName = tableName + ".txt";
                        Table table = TableManager.createTable(fileManager.readFile(fileName));
                        ConsoleWriter.printDescription(table.getColumnDescription());
                        break;
                    }
                    case "print": {
                        String tableName = arguments.get(0);
                        String fileName = tableName + ".txt";
                        Table table = TableManager.createTable(fileManager.readFile(fileName));
                        TableDialog.showTableInDialogWithPagination(table, 5);
                        break;
                    }
                    case "export": {
                        String tableName = arguments.get(0);
                        String fileName = arguments.get(1);
                        String tableFileName = tableName + ".txt";

                        List<String> tableData = fileManager.readFile(tableFileName);
                        fileManager.writeTableInFile(fileName, tableData);
                        fileManager.writeInCatalogFile("catalog.txt", fileName);

                        ConsoleWriter.printDescription("Table " + tableName + " exported in file " + fileName + " successfully.");
                        break;
                    }
                    case "select": {
                        int columnIndex = Integer.parseInt(arguments.get(0));
                        String value = arguments.get(1);
                        String tableName = arguments.get(2);
                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
                        TableDialog.showTableInDialogWithPagination(table.selectRowsByColumnValue(columnIndex, value), 10);
                        break;
                    }
                    case "addcolumn": {
                        String tableName = arguments.get(0);
                        String columnName = arguments.get(1);
                        DataType columnType = DataType.fromString(arguments.get(2));
                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

                        table.addColumn(columnName, columnType);
                        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());
                        ConsoleWriter.printDescription("Column added successfully.");
                        break;
                    }
                    case "update": {
                        String tableName = arguments.get(0);
                        int searchColumnIndex = Integer.parseInt(arguments.get(1));
                        String searchValue = arguments.get(2);
                        int targetColumnIndex = Integer.parseInt(arguments.get(3));
                        String targetValue = arguments.get(4);
                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

                        table.updateRowsByColumnValue(searchColumnIndex, searchValue, targetColumnIndex, targetValue);
                        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());

                        ConsoleWriter.printDescription("Row updated successfully.");
                        break;
                    }
                    case "delete": {
                        String tableName = arguments.get(0);
                        int searchColumnIndex = Integer.parseInt(arguments.get(1));
                        String searchValue = arguments.get(2);
                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

                        table.deleteRowsByColumnValue(searchColumnIndex, searchValue);
                        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());

                        ConsoleWriter.printDescription("Row deleted successfully.");
                        break;
                    }
                    case "insert": {
                        String tableName = arguments.get(0);
                        List<String> values = arguments.subList(1, arguments.size());
                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

                        table.addRow(values);
                        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());

                        ConsoleWriter.printDescription("Row inserted successfully.");
                        break;
                    }
                    case "innerjoin": {
                        String table1Name = arguments.get(0);
                        int col1 = Integer.parseInt(arguments.get(1));
                        String table2Name = arguments.get(2);
                        int col2 = Integer.parseInt(arguments.get(3));

                        Table t1 = TableManager.createTable(fileManager.readFile(table1Name + ".txt"));
                        Table t2 = TableManager.createTable(fileManager.readFile(table2Name + ".txt"));
                        Table joined = TableManager.innerJoinTables(t1, col1, t2, col2);

                        String joinedName = table1Name + "_" + table2Name;
                        fileManager.writeTableInFile(joinedName + ".txt", joined.getTableData());
                        fileManager.writeInCatalogFile("catalog.txt", joinedName + ".txt");

                        System.out.println("Joined table created: " + joinedName);
                        break;
                    }
                    case "rename": {
                        fileManager.renameTable(arguments.get(0), arguments.get(1));
                        break;
                    }
                    case "count": {
                        String tableName = arguments.get(0);
                        int columnIndex = Integer.parseInt(arguments.get(1));
                        String searchValue = arguments.get(2);

                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
                        int count = table.countRowsByColumnValue(columnIndex, searchValue);
                        ConsoleWriter.printDescription("Matching rows: " + count);
                        break;
                    }
                    case "aggregate": {
                        String tableName = arguments.get(0);
                        int searchColumnIndex = Integer.parseInt(arguments.get(1));
                        String searchColumnValue = arguments.get(2);
                        int targetColumnIndex = Integer.parseInt(arguments.get(3));
                        AggregateOperation operation = AggregateOperation.fromString(arguments.get(4));

                        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
                        double result = table.aggregate(searchColumnIndex, searchColumnValue, targetColumnIndex, operation);
                        ConsoleWriter.printDescription(String.valueOf(result));
                        break;
                    }
                    default:
                        ErrorHandler.printException("Unknown command.");
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                ErrorHandler.printException("Not enough arguments for command: " + command);
            } catch (Exception e) {
                ErrorHandler.printException("Error executing command: " + e.getMessage());
            }
        }
    }

    private static List<String> extractArguments(String input) {
        List<String> args = new ArrayList<>();
        Matcher matcher = Pattern.compile("<(.*?)>").matcher(input);

        while (matcher.find()) {
            args.add(matcher.group(1).trim());
        }
        return args;
    }
}