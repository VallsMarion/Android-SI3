package edu.polytech.concertcare;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import java.util.ArrayList;
import java.util.List;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;


public class StaffMapFragment extends Fragment {

    private MapView map;
    private RecyclerView staffPointsRecyclerView;
    private StaffPointsAdapter staffPointsAdapter;
    private List<StaffPoint> staffPointsList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getActivity().getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_staffmap, container, false);

        map = rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        GeoPoint startPoint = new GeoPoint(43.60520, 7.00517);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(startPoint);

        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("you", "where you are", new GeoPoint(43.65020, 7.00517));
        items.add(home);
        items.add(new OverlayItem("rdv", "point de rdv", new GeoPoint(43.64950, 7.00517)));

        Drawable marker = home.getMarker(0);
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(
                getActivity().getApplicationContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        map.getOverlays().add(mOverlay);

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
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }
}
