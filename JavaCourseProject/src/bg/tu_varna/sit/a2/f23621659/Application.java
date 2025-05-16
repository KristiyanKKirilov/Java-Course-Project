package bg.tu_varna.sit.a2.f23621659;

import bg.tu_varna.sit.a2.f23621659.commands.*;
import bg.tu_varna.sit.a2.f23621659.factory.CommandFactory;
import bg.tu_varna.sit.a2.f23621659.interfaces.Command;
import bg.tu_varna.sit.a2.f23621659.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = FileManager.getInstance();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("end")) {
                System.exit(0);
            }

            String commandName;
            List<String> arguments;

            int spaceIndex = input.indexOf(" ");

            if (spaceIndex != -1) {
                commandName = input.substring(0, spaceIndex).toLowerCase();
                arguments = extractArguments(input.substring(spaceIndex));
            } else {
                commandName = input.toLowerCase();
                arguments = new ArrayList<>();
            }

            try {
                Command command = CommandFactory.getCommand(commandName);

                if(command != null) {
                    command.execute(fileManager, arguments);
                } else {
                    ErrorHandler.printException("Unknown command");
                }

            } catch (IndexOutOfBoundsException e) {
                ErrorHandler.printException("Not enough arguments for command: " + commandName);
            } catch (Exception e) {
                ErrorHandler.printException("Error executing command: " + e.getMessage());
            }
        }
    }

    private static List<String> extractArguments(String input) {
        List<String> args = new ArrayList<>();
        Matcher matcher = Pattern.compile("<(.*?)>").matcher(input);

        while (matcher.find()) {
            args.add(matcher.group(1).trim());
        }
        return args;
    }
}