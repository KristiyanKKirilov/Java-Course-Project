package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;

import java.util.List;

public class ExportCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = args  .get(1);
        String tableFileName = tableName + ".txt";

        List<String> tableData = fileManager.readFile(tableFileName);
        fileManager.writeTableInFile(fileName, tableData);
        fileManager.writeInCatalogFile("catalog.txt", fileName);

        ConsoleWriter.printLine("Table " + tableName + " exported in file " + fileName + " successfully");
        ConsoleWriter.printNewLine();
    }
}
