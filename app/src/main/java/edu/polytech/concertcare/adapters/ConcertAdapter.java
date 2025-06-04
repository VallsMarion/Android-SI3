package edu.polytech.concertcare.adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.List;

import edu.polytech.concertcare.interfaces.Notifiable;
import edu.polytech.concertcare.R;
import edu.polytech.concertcare.models.Concert;

public class ConcertAdapter extends RecyclerView.Adapter<ConcertAdapter.ConcertViewHolder> {
    private List<Concert> concertList;
    private Notifiable callBackActivity;

    public static class ConcertViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView details;
        public ImageView image;
        public TextView moreInfo;
        public Button requests;

        public ConcertViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.concertTitle);
            details = view.findViewById(R.id.concertDetails);
            image = view.findViewById(R.id.concertImage);
            moreInfo = view.findViewById(R.id.btnMoreInfo);
            requests = view.findViewById(R.id.btnRequests);
        }
    }

    public ConcertAdapter(List<Concert> concertList, Notifiable callBackActivity) {
        this.concertList = concertList;
        this.callBackActivity = callBackActivity;
    }

    @NonNull
    @Override
    public ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.concert_item, parent, false);
        return new ConcertAdapter.ConcertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertViewHolder holder, int position) {
        TextView title = holder.title;
        TextView details = holder.details;
        ImageView image = holder.image;
        TextView moreInfo = holder.moreInfo;
        Button requests =holder.requests;

        Concert concert = concertList.get(position);
        title.setText(concert.title);
        details.setText("Le " + concert.date + "\n" + concert.location);
        Picasso.get().load(concert.imageUrl).into(image);

        holder.itemView.setOnClickListener( click -> {
            Log.d(TAG, "clicked on item #"+position + " : " + concert.id);
            callBackActivity.onDataChange(2, concert.id);
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return concertList.size();
    }
}
