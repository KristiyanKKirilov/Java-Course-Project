package bg.tu_varna.sit.a2.f23621659.commands;

import bg.tu_varna.sit.a2.f23621659.enums.DataType;
import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621659.models.FileManager;
import bg.tu_varna.sit.a2.f23621659.models.Table;
import bg.tu_varna.sit.a2.f23621659.models.TableManager;

import java.util.List;

public class AddColumnCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String columnName = args.get(1);
        DataType columnType = DataType.fromString(args.get(2));
        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

        table.addColumn(columnName, columnType);
        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());
        ConsoleWriter.printLine("Column added successfully.");
    }
}
