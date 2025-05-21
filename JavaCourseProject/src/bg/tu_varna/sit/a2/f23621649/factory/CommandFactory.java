package bg.tu_varna.sit.a2.f23621649.factory;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("import", new ImportCommand());
        commands.put("showtables", new ShowTablesCommand());
        commands.put("describe", new DescribeCommand());
        commands.put("print", new PrintCommand());
        commands.put("export", new ExportCommand());
        commands.put("select", new SelectCommand());
        commands.put("addcolumn", new AddColumnCommand());
        commands.put("update", new UpdateCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("insert", new InsertCommand());
        commands.put("innerjoin", new InnerJoinCommand());
        commands.put("rename", new RenameCommand());
        commands.put("count", new CountCommand());
        commands.put("aggregate", new AggregateCommand());
        commands.put("help", new HelpCommand());
        commands.put("open", new OpenCommand());
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
        commands.put("saveas", new SaveAsCommand());
    }

    public static Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
}

