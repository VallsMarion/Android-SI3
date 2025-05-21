package edu.polytech.concertcare;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class LostItemFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Spinner concertSpinner, itemTypeSpinner;
    private EditText itemDescription, phoneNumber;
    private Button selectImageButton, sendButton;

    public LostItemFragment() {
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



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost_item, container, false);

        concertSpinner = view.findViewById(R.id.concertSpinner);
        itemTypeSpinner = view.findViewById(R.id.itemTypeSpinner);
        itemDescription = view.findViewById(R.id.itemDescription);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        selectImageButton = view.findViewById(R.id.selectImageButton);
        sendButton = view.findViewById(R.id.sendButton);

        selectImageButton.setOnClickListener(v -> openImagePicker());

        sendButton.setOnClickListener(v -> {
            if (notificationService != null) {
                notificationService.sendSuccessNotificationWithImage();
            }

        });

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
        }
    }
}
