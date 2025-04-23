package edu.polytech.concertcare;

import java.io.Serializable;

public class Concert {
    public String title;
    public String date;
    public String location;
    public String imageUrl;
    public String moreInfoText;
    public String requestButtonText;

    public Concert() {}

    public Concert(String title, String date, String location, String imageUrl,
                   String moreInfoText, String requestButtonText) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.imageUrl = imageUrl;
        this.moreInfoText = moreInfoText;
        this.requestButtonText = requestButtonText;
    }
}
