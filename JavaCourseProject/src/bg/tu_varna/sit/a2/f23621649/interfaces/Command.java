package bg.tu_varna.sit.a2.f23621649.interfaces;

import bg.tu_varna.sit.a2.f23621649.models.FileManager;

import java.util.List;

public interface Command {
    void execute(FileManager fileManager, List<String> args);
}
