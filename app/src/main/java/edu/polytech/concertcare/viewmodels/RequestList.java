package edu.polytech.concertcare.viewmodels;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.models.Request;

public class RequestList {
    private static List<Request> requests;

    private static final RequestList instance = new RequestList();

    private RequestList() {
        requests = new ArrayList<>();
    }

    public static RequestList getInstance() {
        return instance;
    }

    public static List<Request> getRequests() {
        return requests;
    }

    public static void addRequest(Request request) {
        requests.add(request);
    }

    public static void addAllRequests(List<Request> requests) {
        RequestList.requests.addAll(requests);
    }
}
