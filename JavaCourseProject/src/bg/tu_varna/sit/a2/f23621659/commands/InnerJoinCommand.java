package bg.tu_varna.sit.a2.f23621659.commands;

import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.FileManager;
import bg.tu_varna.sit.a2.f23621659.models.Table;
import bg.tu_varna.sit.a2.f23621659.models.TableManager;

import java.util.List;

public class InnerJoinCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String table1Name = args.get(0);
        int col1 = Integer.parseInt(args.get(1));
        String table2Name = args.get(2);
        int col2 = Integer.parseInt(args.get(3));

        Table t1 = TableManager.createTable(fileManager.readFile(table1Name + ".txt"));
        Table t2 = TableManager.createTable(fileManager.readFile(table2Name + ".txt"));
        Table joined = TableManager.innerJoinTables(t1, col1, t2, col2);

        String joinedName = table1Name + "_" + table2Name;
        fileManager.writeTableInFile(joinedName + ".txt", joined.getTableData());
        fileManager.writeInCatalogFile("catalog.txt", joinedName + ".txt");

        System.out.println("Joined table created: " + joinedName);
    }
}
