package edu.polytech.concertcare;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.Manifest;


public class StaffMapFragment extends Fragment {

    private MapView map;
    private RecyclerView staffPointsRecyclerView;
    private StaffPointsAdapter staffPointsAdapter;
    private List<StaffPoint> staffPointsList = new ArrayList<>();

    private ActivityResultLauncher<String> requestLocationPermissionLauncher;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestLocationPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        enableUserLocation();
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            explainLocationPermissionSettings();
                        }
                        else{
                            explainLocationPermission();
                            centerOnNice();
                        }

                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_staffmap, container, false);

        mapView = rootView.findViewById(R.id.map);

        Configuration.getInstance().setOsmdroidBasePath(new File(requireContext().getCacheDir(), "osmdroid"));
        Configuration.getInstance().setOsmdroidTileCache(new File(requireContext().getCacheDir(), "osmdroid/tiles"));
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(18.0); // Valeur par défaut

        // Demande de permission + localisation
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);

        locationOverlay.runOnFirstFix(() -> {
            GeoPoint myLocation = locationOverlay.getMyLocation();
            Log.d("DEBUG", "GPS Fix received: " + myLocation);
            if (myLocation != null) {
                requireActivity().runOnUiThread(() -> {
                    mapView.getController().setZoom(18.0);
                    mapView.getController().animateTo(myLocation);
                });
            }
        });


        staffPointsRecyclerView = rootView.findViewById(R.id.staffPointsList);
        staffPointsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        staffPointsList.add(new StaffPoint("Stand information rue de la musique", "Ouvert de 8:00 à 13:00", 0.4));
        staffPointsList.add(new StaffPoint("Stand accès PMR avenue jazz", "Ouvert de 8:00 à 13:00", 1.8));
        staffPointsList.add(new StaffPoint("Stand information rue de la musique", "Ouvert de 8:00 à 13:00", 2.3));

        staffPointsAdapter = new StaffPointsAdapter(staffPointsList);
        staffPointsRecyclerView.setAdapter(staffPointsAdapter);




        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }


    private void enableUserLocation() {
        if (mapView == null) return;

        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);

        locationOverlay.runOnFirstFix(() -> {
            GeoPoint myLocation = locationOverlay.getMyLocation();
            Log.d("DEBUG", "Location fix: " + myLocation);
            if (myLocation != null) {
                requireActivity().runOnUiThread(() -> {
                    mapView.getController().setZoom(18.0);
                    mapView.getController().animateTo(myLocation);
                });
            }
        });
    }



    private void explainLocationPermission() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Permission de localisation requise")
                .setMessage("Cette application a besoin de votre localisation pour afficher votre position sur la carte.")
                .setPositiveButton("Autoriser", (dialog, which) -> {
                    requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void explainLocationPermissionSettings() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Permission de localisation blolquée")
                .setMessage("Vous devez activer la localisation manuellement dans les paramètres.")
                .setPositiveButton("Ouvrir les paramètres", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void centerOnNice() {
        if (mapView != null) {
            GeoPoint nicePoint = new GeoPoint(43.7102, 7.2620);
            mapView.getController().setZoom(18.0);
            mapView.getController().setCenter(nicePoint);
        }
    }

}
