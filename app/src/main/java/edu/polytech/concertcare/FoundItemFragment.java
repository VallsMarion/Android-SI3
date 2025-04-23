package edu.polytech.concertcare;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FoundItemFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner concertSpinner, itemTypeSpinner;
    private EditText itemDescription, phoneNumber;
    private Button selectImageButton, sendButton;
    private Uri selectedImageUri;

    public FoundItemFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_item, container, false);

        concertSpinner = view.findViewById(R.id.concertSpinner);
        itemTypeSpinner = view.findViewById(R.id.itemTypeSpinner);
        itemDescription = view.findViewById(R.id.itemDescription);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        selectImageButton = view.findViewById(R.id.selectImageButton);
        sendButton = view.findViewById(R.id.sendButton);

        selectImageButton.setOnClickListener(v -> openImagePicker());

        sendButton.setOnClickListener(v -> {
            //todo
        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
        }
    }
}
