package edu.curtin.calendar.lib;

import java.time.*;
import java.util.*;

public class Calendar {
    TerminalGrid terminalGrid;
    LocalDateTime now;

    List<List<String>> data;
    List<String> dayHeadings;
    List<String> hourHeadings;

    public Calendar() {
        terminalGrid = TerminalGrid.create();
        now = LocalDateTime.now();

        data = new ArrayList<List<String>>();
        dayHeadings = new ArrayList<String>();
        hourHeadings = new ArrayList<String>();
    }

    public void nextDay() {
        now = now.plusDays(1);
        display();
    }

    public void previousDay() {
        now = now.minusDays(1);
        display();
    }

    public void nextWeek() {
        now = now.plusWeeks(1);
        display();
    }

    public void previousWeek() {
        now = now.minusWeeks(1);
        display();
    }

    public void nextMonth() {
        now = now.plusMonths(1);
        display();
    }

    public void previousMonth() {
        now = now.minusMonths(1);
        display();
    }

    public void nextYear() {
        now = now.plusYears(1);
        display();
    }

    public void previousYear() {
        now = now.minusYears(1);
        display();
    }

    public void today() {
        now = LocalDateTime.now();
        display();
    }

    public void display() {
        data.clear();
        for (int i = 0; i < 24; i++) {
            data.add(Arrays.asList("", "", "", "", "", "", ""));
        }

        dayHeadings.clear();
        for (int i = 0; i < 7; i++) {
            dayHeadings.add(now.plusDays(i).getDayOfWeek().toString());
        }

        hourHeadings.clear();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                hourHeadings.add("0" + i + ":00");
            }
            else {
                hourHeadings.add(i + ":00");
            }
        }

        System.out.println("\n\n\n\n=====================================================================================\n\n\n\n");
        terminalGrid.print(data, hourHeadings, dayHeadings);
    }
}