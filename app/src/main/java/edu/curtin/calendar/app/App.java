package edu.curtin.calendar.app;

import java.util.*;
import java.io.*;

import edu.curtin.calendar.lib.Calendar;
import edu.curtin.calendar.lib.Plugin;
import edu.curtin.calendar.lib.Script;
import edu.curtin.calendar.parser.MyParser;
import edu.curtin.calendar.parser.ParseException;

public class App {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag("en-AU"));
        List<Plugin> pluginList = new ArrayList<>();
        List<Script> scriptList = new ArrayList<>();

        Calendar calendar = new Calendar(bundle);
        boolean running = true;
        String input = "";
        String output = "";
        String fileName = args[0];
        String encoding = "UTF-8";

        if (args.length != 1) {
            System.out.println("Please enter a file name as part of the arguments.");
        } else {
            if (fileName.contains("utf16")) {
                encoding = "UTF-16";
            } else if (fileName.contains("utf32")) {
                encoding = "UTF-32";
            }

            try {
                MyParser parser = new MyParser(new InputStreamReader(new FileInputStream(fileName), encoding));
                parser.inputFile(calendar, pluginList, scriptList);

                Scanner scanner = new Scanner(System.in);

                calendar.display();

                while (running) {
                    if (output.equals("")) {
                        output = "\u001B[34m" + bundle.getString("enter_command") + ":\u001B[0m\n";
                    }
                    printMenu(bundle, output);
                    output = "";

                    input = scanner.nextLine();

                    switch (input) {
                        case "+d":
                            calendar.nextDay();
                            break;
                        case "+w":
                            calendar.nextWeek();
                            break;
                        case "+m":
                            calendar.nextMonth();
                            break;
                        case "+y":
                            calendar.nextYear();
                            break;
                        case "-d":
                            calendar.previousDay();
                            break;
                        case "-w":
                            calendar.previousWeek();
                            break;
                        case "-m":
                            calendar.previousMonth();
                            break;
                        case "-y":
                            calendar.previousYear();
                            break;
                        case "t":
                            calendar.today();
                            break;
                        case "s":
                            output = "\u001B[34m" + bundle.getString("enter_search") + ":\u001B[0m\n";
                            calendar.display();
                            printMenu(bundle, output);
                            input = scanner.nextLine();
                            calendar.search(input);
                            output = "";
                            break;
                        case "l":
                            output = "\u001B[34m" + bundle.getString("enter_locale") + ":\u001B[0m\n";
                            calendar.display();
                            printMenu(bundle, output);
                            input = scanner.nextLine();
                            bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag(input));
                            calendar.updateBundle(bundle);
                            output = "";
                            break;
                        case "q":
                            running = false;
                            break;
                        case "":
                            output = "";
                            break;
                        default:
                            output = "\u001B[31m" + bundle.getString("invalid_command") + "\u001B[0m\n";
                            break;
                    }
                    calendar.display();
                }
                scanner.close();
            } catch (ParseException | IOException error) {
                System.out.println("\u001B[31m" + error.getMessage() + "\u001B[0m");
            }
        }
    }

    public static void printMenu(ResourceBundle bundle, String output) {
        System.out.print("+d : " + bundle.getString("next_day") + "\n" +
                "+w : " + bundle.getString("next_week") + "\n" +
                "+m : " + bundle.getString("next_month") + "\n" +
                "+y : " + bundle.getString("next_year") + "\n" +
                "-d : " + bundle.getString("previous_day") + "\n" +
                "-w : " + bundle.getString("previous_week") + "\n" +
                "-m : " + bundle.getString("previous_month") + "\n" +
                "-y : " + bundle.getString("previous_year") + "\n" +
                "t  : " + bundle.getString("today") + "\n" +
                "s  : " + bundle.getString("search") + "\n" +
                "l  : " + bundle.getString("change_locale") + "\n" +
                "q  : " + bundle.getString("quit") + "\n" +
                output);
    }
}