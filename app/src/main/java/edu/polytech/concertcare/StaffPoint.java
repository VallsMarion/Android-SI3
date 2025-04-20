package edu.polytech.concertcare;

public class StaffPoint {
    private String name;
    private String details;
    private double distance;

    public StaffPoint(String name, String details, double distance) {
        this.name = name;
        this.details = details;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public double getDistance() {
        return distance;
    }
}
