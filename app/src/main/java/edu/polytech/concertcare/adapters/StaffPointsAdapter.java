package edu.polytech.concertcare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.models.StaffPoint;

public class StaffPointsAdapter extends RecyclerView.Adapter<StaffPointsAdapter.StaffPointViewHolder> {

    private List<StaffPoint> staffPointsList;

    public static class StaffPointViewHolder extends RecyclerView.ViewHolder {
        public ImageView staffPointIcon;
        public TextView staffPointName;
        public TextView staffPointDetails;
        public TextView staffPointDistance;

        public StaffPointViewHolder(View view) {
            super(view);
            staffPointIcon = view.findViewById(R.id.staffPointIcon);
            staffPointName = view.findViewById(R.id.staffPointName);
            staffPointDetails = view.findViewById(R.id.staffPointDetails);
            staffPointDistance = view.findViewById(R.id.staffPointDistance);
        }
    }

    public StaffPointsAdapter(List<StaffPoint> staffPoints) {
        staffPointsList = staffPoints;
    }

    @Override
    public StaffPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_point_item, parent, false);
        return new StaffPointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaffPointViewHolder holder, int position) {
        StaffPoint staffPoint = staffPointsList.get(position);
        holder.staffPointName.setText(staffPoint.getName());
        holder.staffPointIcon.setImageResource(R.drawable.ic_map_marker);
    }

    @Override
    public int getItemCount() {
        return staffPointsList.size();
    }
}
