package edu.curtin.lib;

import java.time.*;
import java.util.*;

/**
 * Represents an event with a date, time, duration, title, and resource bundle.
 * Provides methods to get and set the event's properties, as well as a method
 * to get a display string.
 * Implements the Comparable interface to allow sorting by date and time.
 */
public class Event implements Comparable<Event> {
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private boolean isAllDay;
    private String title;
    private ResourceBundle bundle;

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(date, time);
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDate(String newDate) {
        String[] dateSplit = newDate.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        Month month = Month.of(Integer.parseInt(dateSplit[1]));
        this.date = LocalDate.of(year, month, Integer.parseInt(dateSplit[2]));
    }

    public void setTime(String newTime) {
        String[] timeSplit = newTime.toString().split(":");
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);
        int second = Integer.parseInt(timeSplit[2]);
        this.time = LocalTime.of(hour, minute, second);
        isAllDay = false;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        isAllDay = false;
        this.duration = Integer.parseInt(duration);
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay() {
        duration = 0;
        time = LocalTime.of(0, 0, 0);
        isAllDay = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplay() {
        String display = title;
        if (!isAllDay) {
            display += " " + bundle.getString("at") + " " + time.toString() + " " + bundle.getString("for") + " "
                    + duration + " " + bundle.getString("mins");
        }
        return display + "\n";
    }

    @Override
    public int compareTo(Event otherEvent) {
        return LocalDateTime.of(date, time).compareTo(otherEvent.getLocalDateTime());
    }
}