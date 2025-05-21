package edu.polytech.concertcare.concerts;

import java.util.ArrayList;
import java.util.List;

public class ConcertList {
    private List<Concert> concerts;

    private ConcertList() {
        concerts = new ArrayList<>();
    }

    private static class Holder {
        private static final ConcertList INSTANCE = new ConcertList();
    }

    public static ConcertList getInstance() {
        return Holder.INSTANCE;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void addConcert(Concert concert) {
        concerts.add(concert);
    }

    public void addAllConcerts(List<Concert> concerts) {
        this.concerts.addAll(concerts);
    }
}
