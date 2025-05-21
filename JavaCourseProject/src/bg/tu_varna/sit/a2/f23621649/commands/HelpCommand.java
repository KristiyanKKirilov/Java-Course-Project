package bg.tu_varna.sit.a2.f23621649.commands;

import bg.tu_varna.sit.a2.f23621649.interfaces.Command;
import bg.tu_varna.sit.a2.f23621649.models.ConsoleWriter;
import bg.tu_varna.sit.a2.f23621649.models.FileManager;

import java.util.List;

public class HelpCommand implements Command {

    @Override
    public void execute(FileManager fileManager, List<String> args) {
        printHelp();
    }

    private void printHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(" AVAILABLE COMMANDS:\n\n");

        sb.append(" FILE COMMANDS:\n");
        sb.append("  open <filename>       : Open a file from the 'database/' folder\n");
        sb.append("  close                 : Close the currently open file\n");
        sb.append("  save                  : Save changes to the currently open file\n");
        sb.append("  saveas <newfilename>  : Save current file content to a new file\n");

        sb.append("  import <filename>             - Imports a new table from the 'imports/' folder. Fails if table name exists.\n");
        sb.append("  export <name> <filename>      - Exports a table to a specified file.\n");
        sb.append("  rename <old> <new>            - Renames a table file. New name must be unique.\n");
        sb.append("  showtables                    - Displays a list of all tables currently in the database.\n");

        sb.append("\n TABLE INSPECTION:\n");
        sb.append("  describe <name>               - Shows column types of the specified table.\n");
        sb.append("  print <name>                  - Displays all rows of a table with paging (next, prev, exit).\n");

        sb.append("\n QUERY & MODIFY DATA:\n");
        sb.append("  select <column-n> <value> <table> - Shows rows where column <n> equals <value> (paginated).\n");
        sb.append("  update <table> <search-col> <search-val> <target-col> <new-val> - Updates matching rows.\n");
        sb.append("  delete <table> <search-col> <search-val> - Deletes rows with matching column values.\n");
        sb.append("  insert <table> <val1> ... <valN> - Inserts a new row with the specified column values.\n");

        sb.append("\n TABLE STRUCTURE:\n");
        sb.append("  addcolumn <table> <col-name> <type> - Adds a new column (initially empty) to the table.\n");

        sb.append("\n RELATIONAL OPERATIONS:\n");
        sb.append("  innerjoin <table1> <col1> <table2> <col2> - Performs inner join on the two tables by the specified columns.\n");

        sb.append("\n AGGREGATE OPERATIONS:\n");
        sb.append("  count <table> <col> <value> - Counts the number of rows where <col> matches <value>.\n");
        sb.append("  aggregate <table> <search-col> <search-val> <target-col> <op> - Performs sum, product, max, or min.\n");

        sb.append("\n INFO:\n");
        sb.append("  help                          - Displays this help information.\n");
        sb.append("  exit                          - Exits the application immediately.\n");

        sb.append("\n NOTES:\n");
        sb.append("  - Arguments must be enclosed in angle brackets, e.g., <filename>.\n");
        sb.append("  - Column numbers (like <column-n>) are 1-based.\n");
        sb.append("  - NULL values are supported where applicable.\n");
        sb.append("  - Paging commands (next, prev, exit) are supported during table viewing.\n");

        ConsoleWriter.printLine(sb.toString());
    }

}
