package edu.curtin.api;

public interface APIInterface {
    void addNewEvent(String title, String date, String time, String duration);

    void addNewEvent(String title, String date);
}