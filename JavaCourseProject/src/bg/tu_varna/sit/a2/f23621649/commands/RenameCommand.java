package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;

import java.util.List;

public class RenameCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String oldName = args.get(0);
        String newName = args.get(1);
        fileManager.renameTable(oldName, newName);
    }
}
