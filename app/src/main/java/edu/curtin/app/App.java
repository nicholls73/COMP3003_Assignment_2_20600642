package edu.curtin.app;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import org.python.util.PythonInterpreter;

import edu.curtin.parser.MyParser;
import edu.curtin.parser.ParseException;
import edu.curtin.api.PluginInterface;
import edu.curtin.lib.Calendar;
import edu.curtin.lib.Plugin;
import edu.curtin.lib.Script;

public class App {
    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag("en-AU"));
        List<Plugin> pluginList = new ArrayList<>();
        List<Script> scriptList = new ArrayList<>();

        Calendar calendar = new Calendar(bundle);
        boolean running = true;
        String input;
        String output = ""; // Used to allow the menu to display an output after a user enters an input.
        String fileName;
        String encoding = "UTF-8";

        if (args.length == 1) { // if correct number of arguments
            fileName = args[0];
            if (fileName.contains("utf16")) {
                encoding = "UTF-16";
            } else if (fileName.contains("utf32")) {
                encoding = "UTF-32";
            }

            try (Scanner scanner = new Scanner(System.in);) {
                MyParser parser = new MyParser(new InputStreamReader(new FileInputStream(fileName), encoding));
                parser.inputFile(calendar, pluginList, scriptList);

                runScripts(scriptList, calendar);
                runPlugins(pluginList, calendar);

                calendar.display();

                while (running) {
                    if (output.equals("")) { // If not output, display the default output.
                        output = "\u001B[34m" + bundle.getString("enter_command") + ":\u001B[0m\n";
                    }
                    printMenu(bundle, output);
                    output = ""; // Reset the output.

                    input = scanner.nextLine();

                    switch (input) {
                        case "+d": // next day
                            calendar.nextDay();
                            break;
                        case "+w": // next week
                            calendar.nextWeek();
                            break;
                        case "+m": // next month
                            calendar.nextMonth();
                            break;
                        case "+y": // next year
                            calendar.nextYear();
                            break;
                        case "-d": // previous day
                            calendar.previousDay();
                            break;
                        case "-w": // previous week
                            calendar.previousWeek();
                            break;
                        case "-m": // previous month
                            calendar.previousMonth();
                            break;
                        case "-y": // previous year
                            calendar.previousYear();
                            break;
                        case "t": // today
                            calendar.today();
                            break;
                        case "s": // search
                            output = "\u001B[34m" + bundle.getString("enter_search") + ":\u001B[0m\n";
                            calendar.display();
                            printMenu(bundle, output);
                            input = scanner.nextLine();
                            calendar.search(input);
                            output = "";
                            break;
                        case "l": // change locale
                            output = "\u001B[34m" + bundle.getString("enter_locale") + ":\u001B[0m\n";
                            calendar.display();
                            printMenu(bundle, output);
                            input = scanner.nextLine();
                            bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag(input));
                            calendar.updateBundle(bundle);
                            output = "";
                            break;
                        case "q": // quit
                            running = false;
                            break;
                        case "": // if empty input
                            output = "";
                            break;
                        default: // if invalid input
                            output = "\u001B[31m" + bundle.getString("invalid_command") + "\u001B[0m\n";
                            break;
                    }
                    calendar.display();
                }
            } catch (ParseException | IOException | ClassNotFoundException | NoSuchMethodException
                    | InstantiationException | IllegalAccessException | InvocationTargetException error) {
                System.out.println("\u001B[31m" + error + "\u001B[0m");
            }
        } else { // if incorrect number of arguments
            System.out.println("Please enter a file name as part of the arguments.");
        }
    }

    private static void runPlugins(List<Plugin> pluginList, Calendar calendar)
            throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Plugin plugin : pluginList) { // For each plugin in the list.
            Class<?> theClass = Class.forName(plugin.getId());

            Constructor<?> constructor = theClass.getDeclaredConstructor();

            PluginInterface instance = (PluginInterface) constructor.newInstance();

            Field[] fields = theClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(instance, plugin.getArgument(field.getName()));
            }
            instance.start(calendar);
        }
    }

    private static void runScripts(List<Script> scriptList, Calendar calendar) {
        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.set("calendar", calendar);

            for (Script script : scriptList) {
                interpreter.exec(script.getCode());
            }
        }
    }

    private static void printMenu(ResourceBundle bundle, String output) {
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