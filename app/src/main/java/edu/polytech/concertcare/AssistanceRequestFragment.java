package edu.polytech.concertcare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AssistanceRequestFragment extends Fragment {

    private Spinner concertSpinner, requestTypeSpinner;
    private EditText phoneNumber, remarks;
    private Button sendButton;

    public AssistanceRequestFragment() {
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
            //todo
        });

        return view;
    }
}
