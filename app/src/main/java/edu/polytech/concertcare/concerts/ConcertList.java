package edu.polytech.concertcare.concerts;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage a list of concerts. Not thread-safe.
 * All methods are static so we can access them without creating an instance.
 *
 * TODO : Actuellement, pour travailler correctement avec le singleton, il faudrait implémenter un
 * observable/observer pattern pour que les fragments puissent être notifiés des changements.
 * Par manque de temps, nous avons décidé de ne pas le faire et lorsque nous faisons un POST, nous
 * ajoutons la liste des concerts à la main dans le singleton. (A expliquer à l'oral)
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
