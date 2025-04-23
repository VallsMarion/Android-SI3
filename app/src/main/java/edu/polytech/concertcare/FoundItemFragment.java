package edu.polytech.concertcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class FoundItemFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner concertSpinner, itemTypeSpinner;
    private EditText itemDescription, phoneNumber;
    private Button selectImageButton, sendButton;

    public FoundItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_item, container, false);

        sendButton.setOnClickListener(v -> {
            //todo
        });

        return view;
    }

}
