package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;

import java.util.List;

public class SaveAsCommand implements Command {
    @Override
    public void execute(FileManager fileManager, List<String> args) {
        String fileName = args.getFirst();
        fileManager.saveAsFile(fileName);
    }
}
