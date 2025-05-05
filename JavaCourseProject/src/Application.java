import models.FileManager;
import models.TableFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args)
    {
//        Scanner scanner = new Scanner(System.in);

        List<List<String>> persons = Arrays.asList(
                Arrays.asList("ID", "Name", "Job"),
                Arrays.asList("1", "Steve Johnson", "Driver"),
                Arrays.asList("2", "Alex Ford", "Construction worker"),
                Arrays.asList("3", "Peter Griffin", "F1 Racer")
        );
        List<String> formattedData =  TableFormatter.formatData(persons);
        FileManager.writeInFile("persons.txt", formattedData);

        List<List<String>> events = Arrays.asList(
                Arrays.asList("ID", "Name", "City"),
                Arrays.asList("1", "F1 Race", "Monaco"),
                Arrays.asList("2", "Football match", "Paris"),
                Arrays.asList("3", "Parade", "Rome")
        );

        List<String> formattedEventsdData =  TableFormatter.formatData(events);
        FileManager.writeInFile("events.txt", formattedEventsdData);
    }
}