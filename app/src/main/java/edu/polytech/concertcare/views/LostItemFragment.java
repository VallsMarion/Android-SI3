package edu.polytech.concertcare.views;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.NotificationService;
import edu.polytech.concertcare.R;
import edu.polytech.concertcare.models.Concert;
import edu.polytech.concertcare.models.Request;
import edu.polytech.concertcare.viewmodels.ConcertViewModel;
import edu.polytech.concertcare.viewmodels.RequestList;
import edu.polytech.concertcare.viewmodels.RequestViewModel;

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

        RequestViewModel viewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        //populate spinners
        ConcertViewModel concertViewModel = new ViewModelProvider(requireActivity()).get(ConcertViewModel.class);
        concertViewModel.getConcerts().observe(getViewLifecycleOwner(), concertList -> {
            if (concertList != null) {
                List<String> concertTitles = new ArrayList<>();
                concertTitles.add(getString(R.string.select_concert_placeholder));
                for (Concert concert : concertList.getConcerts()) {
                    concertTitles.add(concert.title);
                }

                ArrayAdapter<String> concertAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        concertTitles
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView view = (TextView) super.getView(position, convertView, parent);
                        view.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                        view.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                        return view;
                    }
                };

                concertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                concertSpinner.setAdapter(concertAdapter);
            }
        });


        String[] itemTypesArray = getResources().getStringArray(R.array.item_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                itemTypesArray
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTextColor(position == 0 ? Color.GRAY : Color.BLACK); //set hint color to gray
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(adapter);


        selectImageButton.setOnClickListener(v -> openImagePicker());

        sendButton.setOnClickListener(v -> {
            String description = itemDescription.getText().toString().trim();
            String phone = phoneNumber.getText().toString().trim();
            String concertTitle = concertSpinner.getSelectedItem().toString();
            String itemType = itemTypeSpinner.getSelectedItem().toString();

            if (!description.isEmpty() && !phone.isEmpty() &&
                    concertSpinner.getSelectedItemPosition() != 0 &&
                    itemTypeSpinner.getSelectedItemPosition() != 0) {

                Request request = new Request(concertTitle, itemType, description, phone);

                viewModel.addrequest(request); //notify view model
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_main, new Screen3Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
                itemDescription.setText("");
                phoneNumber.setText("");
                concertSpinner.setSelection(0);
                itemTypeSpinner.setSelection(0);

                if (notificationService != null) {
                    notificationService.sendSuccessNotificationWithImage();
                }

            } else {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
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
