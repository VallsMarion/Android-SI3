package edu.polytech.concertcare;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class FoundItemFragment extends Fragment {

    private static final int REQUEST_CAMERA_CODE = 100;
    private Spinner concertSpinner, itemTypeSpinner;
    private EditText itemDescription, phoneNumber;
    private Button selectImageButton, sendButton;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    public FoundItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Fragment created");
        super.onCreate(savedInstanceState);
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                takePicture();
            } else {
                Log.d(TAG," no permission GRANTED");
                explain();
            }
        });
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

        selectImageButton.setOnClickListener( click -> {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "permission granted");
                takePicture();
            }
            else {
                Log.d(TAG, "launching permission");
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
        sendButton.setOnClickListener(v -> {
            //todo
        });

        return view;
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult( intent, REQUEST_CAMERA_CODE );
        Log.d(TAG, getActivity().toString());

    }

    private void explain() {
        if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission bloquée")
                    .setMessage("La permission caméra a été \"définitivement\" refusée. Vous devez l'activer à nouveau \"manuellement\" depuis les paramètres.")
                    .setPositiveButton("Ouvrir les paramètres", (dialog, which) -> {Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null); intent.setData(uri); startActivity(intent); })
                    .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                    .show(); }
        else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission CAMERA refusée")
                    .setMessage("Impossible d'utiliser la caméra sans permission. Redemander l'autorisation ?")
                    .setPositiveButton("Redemander", (dialog, which) -> requestPermissionLauncher.launch(Manifest.permission.CAMERA) )
                    .setNegativeButton("NON !", null)
                    .show();
        }
    }

    public void receivePhoto(Bitmap imageBitmap) {
        ImageView imagePreview = getView().findViewById(R.id.imagePreview);
        imagePreview.setImageBitmap(imageBitmap);
    }

}
