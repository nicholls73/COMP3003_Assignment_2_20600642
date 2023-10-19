package edu.curtin.calendar.app;

import java.util.Scanner;

import edu.curtin.calendar.lib.Calendar;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
        scanner.close();
    }
}