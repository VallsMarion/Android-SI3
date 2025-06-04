package edu.polytech.concertcare.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.polytech.concertcare.NotificationService;
import edu.polytech.concertcare.R;

public class AssistanceRequestFragment extends Fragment {

    private Spinner concertSpinner, requestTypeSpinner;
    private EditText phoneNumber, remarks;
    private Button sendButton;

    public AssistanceRequestFragment() {
    }

    private NotificationService notificationService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) service;
            notificationService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            notificationService = null;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(requireContext(), NotificationService.class);
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assistance_request, container, false);

        concertSpinner = view.findViewById(R.id.concertSpinner);
        requestTypeSpinner = view.findViewById(R.id.requestTypeSpinner);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        remarks = view.findViewById(R.id.remarks);
        sendButton = view.findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            if (notificationService != null) {
                notificationService.sendSuccessNotificationWithImage();
            }

        });

        return view;
    }
}
