package edu.polytech.concertcare.concerts;

import java.util.List;

public class Concert {
    public String title;
    public String date;
    public String location;
    public String imageUrl;

    public List<StaffPoint> staffPoints;

    public Concert() {}

    public Concert(String title, String date, String location, String imageUrl,
                   List<StaffPoint> staffPoints) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.imageUrl = imageUrl;
        this.staffPoints = staffPoints;
    }
}
