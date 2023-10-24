package edu.curtin.lib;

import java.text.Normalizer;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Calendar {
    TerminalGrid terminalGrid;
    LocalDate currDate;

    String[][] data;
    String[] dayHeadings;
    String[] hourHeadings;

    List<Event> eventList;
    ResourceBundle bundle;

    public Calendar(ResourceBundle bundle) {
        terminalGrid = TerminalGrid.create();
        currDate = LocalDate.now();

        data = new String[25][7];
        dayHeadings = new String[7];
        hourHeadings = new String[25];

        eventList = new ArrayList<Event>();
        this.bundle = bundle;
    }

    public void updateBundle(ResourceBundle bundle) {
        this.bundle = bundle;
        for (Event currEvent : eventList) {
            currEvent.setBundle(bundle);
        }
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void addEvent(Event newEvent) {
        newEvent.setBundle(bundle);
        eventList.add(newEvent);
    }

    public void addNewEvent(String title, String date, String time, String duration) {
        Event newEvent = new Event();
        newEvent.setDate(date);
        newEvent.setTime(time);
        newEvent.setDuration(duration);
        newEvent.setTitle(title);
        newEvent.setBundle(bundle);
        eventList.add(newEvent);
    }

    public void addNewEvent(String title, String date) {
        Event newEvent = new Event();
        newEvent.setDate(date);
        newEvent.setAllDay();
        newEvent.setTitle(title);
        newEvent.setBundle(bundle);
        eventList.add(newEvent);
    }

    public void nextDay() {
        currDate = currDate.plusDays(1);
    }

    public void previousDay() {
        currDate = currDate.minusDays(1);
    }

    public void nextWeek() {
        currDate = currDate.plusWeeks(1);
    }

    public void previousWeek() {
        currDate = currDate.minusWeeks(1);
    }

    public void nextMonth() {
        currDate = currDate.plusMonths(1);
    }

    public void previousMonth() {
        currDate = currDate.minusMonths(1);
    }

    public void nextYear() {
        currDate = currDate.plusYears(1);
    }

    public void previousYear() {
        currDate = currDate.minusYears(1);
    }

    public void today() {
        currDate = LocalDate.now();
    }

    public void search(String searchTerm) {
        Collections.sort(eventList);
        for (Event currEvent : eventList) {
            if (matches(currEvent.getTitle(), searchTerm)) {
                currDate = currEvent.getDate();
                return;
            }
        }
    }

    private boolean matches(String eventTitle, String searchTerm) {
        return Normalizer.normalize(eventTitle, Normalizer.Form.NFKC).toLowerCase().contains(searchTerm.toLowerCase());
    }

    public void display() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 7; j++) {
                data[i][j] = "";
                for (Event currEvent : eventList) {
                    if (currDate.plusDays(j).equals(currEvent.getDate())) {
                        if (currEvent.isAllDay()) {
                            data[24][j] = currEvent.getDisplay();
                        } else if (currEvent.getTime().getHour() == i) {
                            data[i][j] = currEvent.getDisplay();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            String day = "";
            switch (currDate.plusDays(i).getDayOfWeek()) {
                case FRIDAY:
                    day = bundle.getString("friday");
                    break;
                case MONDAY:
                    day = bundle.getString("monday");
                    break;
                case SATURDAY:
                    day = bundle.getString("saturday");
                    break;
                case SUNDAY:
                    day = bundle.getString("sunday");
                    break;
                case THURSDAY:
                    day = bundle.getString("thursday");
                    break;
                case TUESDAY:
                    day = bundle.getString("tuesday");
                    break;
                case WEDNESDAY:
                    day = bundle.getString("wednesday");
                    break;
            }
            // dayHeadings[i] = day + "\n" +
            // currDate.plusDays(i).format(DateTimeFormatter.ofPattern("dd/MM"));
            /*
             * dayHeadings[i] = day + "\n"
             * + currDate.plusDays(i).format(
             * DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(bundle.
             * getLocale()));
             */

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                    .withLocale(bundle.getLocale());
            dayHeadings[i] = day + "\n" + currDate.plusDays(i).format(formatter);
        }

        for (int i = 0; i < 24; i++) {
            hourHeadings[i] = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(bundle.getLocale())
                    .format(LocalTime.of(i, 0));
        }
        hourHeadings[24] = bundle.getString("all_day");
        System.out.println();
        terminalGrid.print(data, hourHeadings, dayHeadings);
    }
}