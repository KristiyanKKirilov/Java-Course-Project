package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.*;

import java.util.List;

public class SelectCommand implements Command {
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public void execute(FileManager fileManager, List<String> args) {
        int columnIndex = Integer.parseInt(args.get(0));
        String value = args.get(1);
        String tableName = args.get(2);

        Table table = TableManager.createTable(fileManager.readFile(tableName + ".txt"));
        List<String> tableData = table.selectRowsByColumnValue(columnIndex, value).getTableData();
        Pagination pagination = new Pagination(tableData, RECORDS_PER_PAGE);

        TableVisualiser tableVisualiser = new TableVisualiser();
        tableVisualiser.display(pagination);
    }
}
