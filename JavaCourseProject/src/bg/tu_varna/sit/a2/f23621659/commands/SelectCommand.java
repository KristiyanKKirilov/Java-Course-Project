package bg.tu_varna.sit.a2.f23621659.commands;

import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621659.models.FileManager;
import bg.tu_varna.sit.a2.f23621659.models.Table;
import bg.tu_varna.sit.a2.f23621659.models.TableManager;

import java.util.List;

public class SelectCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        int columnIndex = Integer.parseInt(args.get(0));
        String value = args.get(1);
        String tableName = args.get(2);

        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
        List<String> tableData = table.selectRowsByColumnValue(columnIndex, value).getTableData();
        ConsoleWriter.printTables(tableData);
    }
}
