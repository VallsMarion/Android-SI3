package edu.polytech.concertcare.models;

public class Request {
    private String title;
    private String description;

    public Request(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
