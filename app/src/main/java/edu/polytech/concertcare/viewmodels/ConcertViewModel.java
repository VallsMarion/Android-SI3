package edu.polytech.concertcare.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.polytech.concertcare.models.Concert;

public class ConcertViewModel extends ViewModel {
    private final MutableLiveData<ConcertList> concertListLiveData = new MutableLiveData<>(ConcertList.getInstance());

    public LiveData<ConcertList> getConcerts() {
        return concertListLiveData;
    }

    public void setConcerts(List<Concert> newConcerts) {
        ConcertList.addAllConcerts(newConcerts);
        concertListLiveData.setValue(ConcertList.getInstance()); // Notifie les observers
    }
}