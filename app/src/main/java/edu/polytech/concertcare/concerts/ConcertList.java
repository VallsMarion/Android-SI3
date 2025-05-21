package edu.polytech.concertcare.concerts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton class to manage a list of concerts. Not thread-safe.
 * All methods are static so we can access them without creating an instance.
 */
public class ConcertList {
    private static List<Concert> concerts;

    private static final ConcertList instance = new ConcertList();

    private ConcertList() {
        concerts = new ArrayList<>();
    }

    public static ConcertList getInstance() {
        return instance;
    }

    public static List<Concert> getConcerts() {
        return concerts;
    }

    public static void addConcert(Concert concert) {
        concerts.add(concert);
    }

    public static void addAllConcerts(List<Concert> concerts) {
        ConcertList.concerts.addAll(concerts);
    }
}
