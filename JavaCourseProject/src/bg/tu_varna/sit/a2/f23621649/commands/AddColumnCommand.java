package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.enums.DataType;
import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.*;

import java.util.List;

public class AddColumnCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = tableName + ".txt";
        String columnName = args.get(1);
        DataType columnType = DataType.fromString(args.get(2));

        if (!fileManager.validateOpenedFile(fileName)) {
            ErrorHandler.printException("Cannot modify table. " + fileName + " is not the opened file.");
            return;
        }

        Table table = TableManager.createTable(fileManager.readFile(fileName));

        table.addColumn(columnName, columnType);
        fileManager.setCurrentTableContent(table.getTableData());

        ConsoleWriter.printLine("Column added successfully (uncommitted)");
        ConsoleWriter.printNewLine();
    }
}
