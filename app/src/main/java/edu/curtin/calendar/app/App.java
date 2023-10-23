package edu.curtin.calendar.app;

import java.util.*;
import java.io.*;

import edu.curtin.calendar.lib.Calendar;
import edu.curtin.calendar.lib.Event;
import edu.curtin.calendar.lib.Plugin;
import edu.curtin.calendar.lib.Script;
import edu.curtin.calendar.parser.MyParser;
import edu.curtin.calendar.parser.ParseException;

public class App {
    public static void main(String[] args) {
        Locale locale = Locale.forLanguageTag("en-AU");
        List<Plugin> pluginList = new ArrayList<>();
        List<Script> scriptList = new ArrayList<>();

        Calendar calendar = new Calendar();
        boolean running = true;
        String input = "";

        try {
            MyParser parser = new MyParser(new FileInputStream("test.txt"));
            parser.inputFile(calendar, pluginList, scriptList);
        }
        catch (ParseException | IOException error) {
            System.out.println(error.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        calendar.display();

        while (running) {
            System.out.print("+d : next day\n" +
                             "+w : next week\n" +
                             "+m : next month\n" +
                             "+y : next year\n" +
                             "-d : previous day\n" +
                             "-w : previous week\n" +
                             "-m : previous month\n" +
                             "-y : previous year\n" +
                             "t  : today\n" +
                             "s  : search\n" +
                             "q  : quit\n" +
                             "Please Enter Command Below:\n");

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
                    System.out.print("Please Enter Search Term Below:\n");
                    input = scanner.nextLine();

                    break;
                case "q":
                    running = false;
                    break;
            }
            calendar.display();
        }
        scanner.close();
    }
}