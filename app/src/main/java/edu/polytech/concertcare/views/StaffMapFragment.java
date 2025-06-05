package edu.polytech.concertcare.views;

import android.app.AlertDialog;
import android.content.Intent;
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

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.Manifest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.adapters.StaffPointsAdapter;
import edu.polytech.concertcare.models.Concert;
import edu.polytech.concertcare.models.StaffPoint;
import edu.polytech.concertcare.viewmodels.ConcertList;
import edu.polytech.concertcare.viewmodels.ConcertViewModel;


public class StaffMapFragment extends Fragment {

    private RecyclerView staffPointsRecyclerView;
    private StaffPointsAdapter staffPointsAdapter;
    private List<StaffPoint> staffPointsList = new ArrayList<>();

    private ActivityResultLauncher<String> requestLocationPermissionLauncher;

    private MapView mapView;
    private MyLocationNewOverlay locationOverlay;
    private Spinner spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this is if wee keep satff point details when you click on a concert
        /*if (getArguments() != null) {
            int concertIndex = getArguments().getInt("concert_index", -1);
            if (concertIndex != -1 && concertIndex < ConcertList.getConcerts().size()) {
                staffPointsList = ConcertList.getConcerts().get(concertIndex).staffPoints;
            }
        }*/

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
        spinner = rootView.findViewById(R.id.staffFilterSpinner);


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
        //locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);


        locationOverlay.runOnFirstFix(() -> {
            GeoPoint myLocation = locationOverlay.getMyLocation();
            Log.d("DEBUG", "GPS Fix received: " + myLocation);
            if (myLocation != null) {
                requireActivity().runOnUiThread(() -> {
                    //mapView.getController().setZoom(18.0);
                    //mapView.getController().animateTo(myLocation);
                });
            }
        });


        staffPointsRecyclerView = rootView.findViewById(R.id.staffPointsList);
        staffPointsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        staffPointsAdapter = new StaffPointsAdapter(staffPointsList);
        staffPointsRecyclerView.setAdapter(staffPointsAdapter);

        ConcertViewModel viewModel = new ViewModelProvider(requireActivity()).get(ConcertViewModel.class);
        viewModel.getConcerts().observe(getViewLifecycleOwner(), concerts -> {
            if (concerts != null) {
                List<Concert> concertList = ConcertList.getConcerts();
                String[] concertTitles = concertList.stream()
                        .map(concert -> concert.title)
                        .toArray(String[]::new);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        concertTitles
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

// Gérer les changements de sélection
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Concert concert = ConcertList.getConcerts().get(position);
                        Log.d("StaffMapFragment", "Selected concert: " + concert.title + " with ID: " + concert.id);
                        staffPointsList.clear();
                        clearMarkers();
                        if (concert.staffPoints != null) {
                            staffPointsList.addAll(concert.staffPoints);
                            for (StaffPoint point : concert.staffPoints) {
                                GeoPoint geoPoint = new GeoPoint(point.getLatitude(), point.getLongitude());
                                addMarker(geoPoint, point.getName(), point.getName());
                            }

                            if(!staffPointsList.isEmpty()) {
                                GeoPoint myLocation = new GeoPoint(staffPointsList.get(0).getLatitude(), staffPointsList.get(0).getLongitude());
                                requireActivity().runOnUiThread(() -> {
                                    mapView.getController().setZoom(18.0);
                                    mapView.getController().animateTo(myLocation);
                                });
                            }
                        }
                        staffPointsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            }
        });


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
        //locationOverlay.enableFollowLocation();
        mapView.getOverlays().add(locationOverlay);

        locationOverlay.runOnFirstFix(() -> {
            GeoPoint myLocation = locationOverlay.getMyLocation();
            Log.d("DEBUG", "Location fix: " + myLocation);
            if (myLocation != null) {
                requireActivity().runOnUiThread(() -> {
                    //mapView.getController().setZoom(18.0);
                    //mapView.getController().animateTo(myLocation);
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

    private void addMarker(GeoPoint point, String title, String description) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setTitle(title);
        marker.setSubDescription(description);
        marker.setPanToView(true);
        mapView.getOverlays().add(marker);
        mapView.invalidate(); // Redessine la carte
    }

    private void clearMarkers() {
        List<Overlay> toRemove = new ArrayList<>();

        for (Overlay overlay : mapView.getOverlays()) {
            if (overlay instanceof Marker) {
                toRemove.add(overlay);
            }
        }

        mapView.getOverlays().removeAll(toRemove);
        mapView.invalidate(); // Redessine la carte
    }


}
