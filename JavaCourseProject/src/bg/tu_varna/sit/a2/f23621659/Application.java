package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.commands.*;
import bg.tu_varna.sit.a2.f23621659.enums.AggregateOperation;
import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
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
                        Command importCommand = new ImportCommand();
                        importCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "showtables":
                        Command showTablesCommand = new ShowTablesCommand();
                        showTablesCommand.execute(fileManager, arguments);
                        break;
                    case "describe": {
                        Command describeCommand = new DescribeCommand();
                        describeCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "print": {
                       Command printCommand = new PrintCommand();
                       printCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "export": {
                        Command exportCommand = new ExportCommand();
                        exportCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "select": {
                       Command selectCommand = new SelectCommand();
                       selectCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "addcolumn": {
                        Command addColumnCommand = new AddColumnCommand();
                        addColumnCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "update": {
                        Command updateCommand = new UpdateCommand();
                        updateCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "delete": {
                        Command deleteCommand = new DeleteCommand();
                        deleteCommand.execute(fileManager, arguments);
                        break;
                    }
                    case "insert": {
                       Command insertCommand = new InsertCommand();
                       insertCommand.execute(fileManager, arguments);
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