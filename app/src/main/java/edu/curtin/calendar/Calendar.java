package edu.curtin.calendar;

import java.time.*;
import java.util.*;

public class Calendar {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        List<List<String>> data = new ArrayList<List<String>>();
        for (int i = 0; i < 24; i++) {
            data.add(Arrays.asList("one", "two", "three", "four", "five", "six", "seven"));
        }

        List<String> rowHeadings = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            rowHeadings.add("hour " + (i + 1));
        }
        String[] colHeadings = { now.getDayOfWeek().toString(), now.getDayOfWeek().plus(1).toString(),
                now.getDayOfWeek().plus(2).toString(), now.getDayOfWeek().plus(3).toString(),
                now.getDayOfWeek().plus(4).toString(), now.getDayOfWeek().plus(5).toString(),
                now.getDayOfWeek().plus(6).toString() };

        TerminalGrid terminalGrid = TerminalGrid.create();

        terminalGrid.print(data, rowHeadings, List.of(colHeadings));
    }
}
