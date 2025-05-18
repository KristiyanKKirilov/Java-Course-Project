package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.*;

import java.util.List;

public class PrintCommand implements Command {
    private static final int PAGE_SIZE = 5;

    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = tableName + ".txt";
        Table table = TableManager.createTable(fileManager.readFile(fileName));
        List<String> tableData = table.getTableData();
        Pagination pagination = new Pagination(tableData, PAGE_SIZE);

        TableVisualiser tableVisualiser = new TableVisualiser();
        tableVisualiser.display(pagination);

    }
}
