package edu.polytech.concertcare.models;

import java.util.List;

public class Concert {
    public String id;
    public String title;
    public String date;
    public String location;
    public String imageUrl;

    public List<StaffPoint> staffPoints;

    public Concert() {}

    public Concert(String id, String title, String date, String location, String imageUrl,
                   List<StaffPoint> staffPoints) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.imageUrl = imageUrl;
        this.staffPoints = staffPoints;
    }
}
