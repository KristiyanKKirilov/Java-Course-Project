package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.enums.AggregateOperation;
import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;
import bg.tu_varna.sit.a2.f23621649.models.Table;
import bg.tu_varna.sit.a2.f23621649.models.TableManager;

import java.util.List;

public class AggregateCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        int searchColumnIndex = Integer.parseInt(args.get(1));
        String searchColumnValue = args.get(2);
        int targetColumnIndex = Integer.parseInt(args.get(3));
        AggregateOperation operation = AggregateOperation.fromString(args.get(4));

        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
        double result = table.aggregate(searchColumnIndex, searchColumnValue, targetColumnIndex, operation);
        ConsoleWriter.printLine(String.valueOf(result));
        ConsoleWriter.printNewLine();
    }
}
