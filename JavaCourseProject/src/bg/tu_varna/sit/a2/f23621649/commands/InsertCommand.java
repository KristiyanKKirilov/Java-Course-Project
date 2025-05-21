package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.*;

import java.util.List;

public class InsertCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = tableName + ".txt";
        List<String> values = args.subList(1, args.size());

        if (!fileManager.validateOpenedFile(fileName)) {
            ErrorHandler.printException("Cannot modify table. " + fileName + " is not the opened file.");
            return;
        }

        Table table = TableManager.createTable(fileManager.readFile(fileName));

        table.addRow(values);
        fileManager.setCurrentTableContent(table.getTableData());

        ConsoleWriter.printLine("Row inserted successfully (uncommitted)");
        ConsoleWriter.printNewLine();
    }
}
