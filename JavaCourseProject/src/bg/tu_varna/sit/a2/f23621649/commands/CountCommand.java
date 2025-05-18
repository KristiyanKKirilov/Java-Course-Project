package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;
import bg.tu_varna.sit.a2.f23621649.models.Table;
import bg.tu_varna.sit.a2.f23621649.models.TableManager;

import java.util.List;

public class CountCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        int columnIndex = Integer.parseInt(args.get(1));
        String searchValue = args.get(2);

        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
        int count = table.countRowsByColumnValue(columnIndex, searchValue);
        ConsoleWriter.printLine("Matching rows: " + count);
    }
}
