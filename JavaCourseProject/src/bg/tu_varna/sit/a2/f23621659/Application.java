package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.models.FileManager;
import bg.tu_varna.sit.a2.f23621659.models.TableFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

//        List<List<String>> cities = Arrays.asList(
//                Arrays.asList("ID", "Name"),
//                Arrays.asList("1", "Monaco"),
//                Arrays.asList("2", "Paris"),
//                Arrays.asList("3", "Rome"),
//                Arrays.asList("4", "Madrid"),
//                Arrays.asList("5", "Zurich")
//        );
//
//        List<String> formattedJobsData =  TableFormatter.formatData(cities);
//        FileManager.writeInFile("cities.txt", formattedJobsData);

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

//        List<List<String>> events = Arrays.asList(
//                Arrays.asList("ID", "Name", "City"),
//                Arrays.asList("1", "F1 Race", "Monaco"),
//                Arrays.asList("2", "Football match", "Paris"),
//                Arrays.asList("3", "Parade", "Rome")
//        );
//
//        List<String> formattedEventsData =  TableFormatter.formatData(events);
//        FileManager.writeInFile("events.txt", formattedEventsData);
//
//        List<String> outputData = FileManager.readFile("persons.txt");
//        for(String data: outputData)
//        {
//            System.out.println(data);
//        }
//
//        FileManager.importTable("departments.txt");

    }
}