package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;
import bg.tu_varna.sit.a2.f23621649.models.Table;
import bg.tu_varna.sit.a2.f23621649.models.TableManager;

import java.util.List;

public class InsertCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        List<String> values = args.subList(1, args.size());
        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));

        table.addRow(values);
        fileManager.updateTableInFile(tableName + ".txt", table.getTableData());

        ConsoleWriter.printLine("Row inserted successfully.");
    }
}
