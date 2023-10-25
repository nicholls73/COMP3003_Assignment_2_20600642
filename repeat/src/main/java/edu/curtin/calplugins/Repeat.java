package edu.curtin.calplugins;

import java.time.*;
import java.time.format.DateTimeFormatter;

import edu.curtin.api.APIInterface;
import edu.curtin.api.PluginInterface;

public class Repeat implements PluginInterface {
    String title;
    String startDate;
    String startTime;
    String duration;
    String repeat;

    @Override
    public void start(APIInterface api) {
        // Remove the double quotes.
        repeat = repeat.substring(1, repeat.length() - 1);

        // Add 1 so that it number of days BETWEEN the events.
        int intervals = Integer.parseInt(repeat) + 1;

        if (startTime == null && duration == null) { // if all day
            addAllDayEvent(api, intervals);
        } else { // if not all day
            addEvent(api, intervals);
        }
    }

    private void addAllDayEvent(APIInterface api, int intervals) {
        // Remove the double quotes.
        startDate = startDate.substring(1, startDate.length() - 1);

        // Build LocalDate so can add a day.
        String[] dateSplit = startDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        Month month = Month.of(Integer.parseInt(dateSplit[1]));
        LocalDate localDate = LocalDate.of(year, month, Integer.parseInt(dateSplit[2]));

        // Loop the amount of events that need to occur in a year.
        for (int i = 0; i < 365 / intervals; i++) {
            api.addNewEvent(title, localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            localDate = localDate.plusDays(intervals);
        }
    }

    private void addEvent(APIInterface api, int intervals) {
        // Remove the double quotes.
        startDate = startDate.substring(1, startDate.length() - 1);
        startTime = startTime.substring(1, startTime.length() - 1);
        duration = duration.substring(1, duration.length() - 1);

        // Build LocalDate so can add a day.
        String[] dateSplit = startDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        Month month = Month.of(Integer.parseInt(dateSplit[1]));
        LocalDate localDate = LocalDate.of(year, month, Integer.parseInt(dateSplit[2]));

        // Loop the amount of events that need to occur in a year.
        for (int i = 0; i < 365 / intervals; i++) {
            api.addNewEvent(title, localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), startTime, duration);
            localDate = localDate.plusDays(intervals);
        }
    }
}