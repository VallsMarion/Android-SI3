package edu.polytech.concertcare.models;

public class Request {
    private String title;
    private String itemType;
    private String description;
    private String phone;

    public Request(String concertTitle, String itemType, String description, String phone) {
        this.title = concertTitle;
        this.itemType = itemType;
        this.description = description;
        this.phone = phone;
    }
    public String getConcertTitle() {
        return title;
    }

    public String getItemType() {
        return itemType;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }
}
