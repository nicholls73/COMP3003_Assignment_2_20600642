package edu.curtin.calendar.lib;

import java.time.*;

public class Event {
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private boolean isAllDay;
    private String title;

    /*public Event(LocalDateTime dateTime, int duration, boolean isAllDay, String title) {
        this.dateTime = dateTime;
        this.duration = duration;
        this.isAllDay = isAllDay;
        this.title = title;
    }*/

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
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

    public void setDuration(int duration) {
        isAllDay = false;
        this.duration = duration;
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
}