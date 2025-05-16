package bg.tu_varna.sit.a2.f23621659.commands;

import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.FileManager;

import java.util.List;

public class ShowTablesCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        fileManager.showTables();
    }
}
