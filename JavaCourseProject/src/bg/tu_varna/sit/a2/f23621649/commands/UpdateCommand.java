package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.*;

import java.util.List;

public class UpdateCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = tableName + ".txt";
        int searchColumnIndex = Integer.parseInt(args.get(1));
        String searchValue = args.get(2);
        int targetColumnIndex = Integer.parseInt(args.get(3));
        String targetValue = args.get(4);

        if (!fileManager.validateOpenedFile(fileName)) {
            ErrorHandler.printException("Cannot modify table. " + fileName + " is not the opened file.");
            return;
        }

        Table table = TableManager.createTable(fileManager.readFile(fileName));

        table.updateRowsByColumnValue(searchColumnIndex, searchValue, targetColumnIndex, targetValue);
        fileManager.setCurrentTableContent(table.getTableData());

        ConsoleWriter.printLine("Row updated successfully (uncommited)");
        ConsoleWriter.printNewLine();
    }
}
