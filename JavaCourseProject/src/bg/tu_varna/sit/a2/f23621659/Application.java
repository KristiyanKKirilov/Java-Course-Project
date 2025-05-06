package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.models.FileManager;
import bg.tu_varna.sit.a2.f23621659.models.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

//        List<String> headers = Arrays.asList("ID", "Name");
//        List<String> types = Arrays.asList("int", "string");
//
//        Table jobsTable = new Table(headers, types);
//        jobsTable.addRow(Arrays.asList("1", "Driver"));
//        jobsTable.addRow(Arrays.asList("2", "Construction worker"));
//        jobsTable.addRow(Arrays.asList("3", "F1 Racer"));
//        jobsTable.addRow(Arrays.asList("4", "Cook Chef"));
//        jobsTable.addRow(Arrays.asList("5", "Zoo keeper"));
//
//        List<String> seasonsTableData = jobsTable.getTableData();
//        FileManager.writeInFile("jobs.txt", seasonsTableData);

        String input;
        while(!(input = scanner.nextLine()).equals("END")) {
            String[] command = input.split(" ");

            switch(command[0]) {
                case "import":
                    String fileName = command[1];
                    FileManager.importTable(fileName);
                    break;
                case "showtables":
                    FileManager.showTables();
                    break;

                default:
                    break;
            }
        }

    }
}