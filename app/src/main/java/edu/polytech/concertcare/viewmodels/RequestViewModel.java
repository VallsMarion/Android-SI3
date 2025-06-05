package edu.polytech.concertcare.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.polytech.concertcare.models.Concert;
import edu.polytech.concertcare.models.Request;

public class RequestViewModel extends ViewModel {
    private final MutableLiveData<RequestList> requestListLiveData = new MutableLiveData<>(RequestList.getInstance());

    public LiveData<RequestList> getRequests() {
        return requestListLiveData;
    }

    public void addrequest(Request request) {
        RequestList.addRequest(request);
        requestListLiveData.setValue(RequestList.getInstance()); // notifie les observers
    }
}
