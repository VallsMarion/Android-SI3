package edu.polytech.concertcare.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.polytech.concertcare.models.Concert;

public class ConcertViewModel extends ViewModel {
    private final MutableLiveData<List<Concert>> concerts = new MutableLiveData<>();

    public LiveData<List<Concert>> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> data) {
        concerts.setValue(data);
    }
}
