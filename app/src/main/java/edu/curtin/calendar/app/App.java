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
        List<Event> eventList = new ArrayList<>();
        List<Plugin> pluginList = new ArrayList<>();

        try {
            MyParser parser = new MyParser(new FileInputStream("test2.txt"));
            parser.inputFile(eventList, pluginList);
            for (int i = 0; i < eventList.size(); i++) {
                System.out.println(eventList.get(i).getTitle());
            }
        }
        catch (ParseException | IOException error) {
            System.out.println(error.getMessage());
        }

        /*Scanner scanner = new Scanner(System.in);

        Calendar calendar = new Calendar();
        boolean running = true;
        String command = "";

        calendar.display();

        while (running) {
            System.out.print("Please Enter Command Below.");
            command = scanner.nextLine();
            
            switch (command) {
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
                case "q":
                    running = false;
                break;
            }
        }
        scanner.close();*/
    }
}