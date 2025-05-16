package bg.tu_varna.sit.a2.f23621659.commands;

import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621659.models.FileManager;

import java.util.List;

public class ImportCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String tableName = args.get(0);
        String fileName = tableName + ".txt";

        fileManager.importTable(fileName);
        ConsoleWriter.printDescription("Table imported successfully.");
    }
}
