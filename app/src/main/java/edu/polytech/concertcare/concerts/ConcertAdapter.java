package edu.polytech.concertcare.concerts;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.squareup.picasso.Picasso;
import java.util.List;

import edu.polytech.concertcare.Notifiable;
import edu.polytech.concertcare.R;

public class ConcertAdapter extends BaseAdapter {
    private List<Concert> concertList;
    private LayoutInflater inflater;

    private Notifiable callBackActivity;;

    public ConcertAdapter(List<Concert> concertList, Notifiable callBackActivity) {
        this.concertList = concertList;
        this.callBackActivity = callBackActivity;
        this.inflater = LayoutInflater.from(callBackActivity.getContext());
    }


    @Override
    public int getCount() {
        return concertList.size();
    }

    @Override
    public Object getItem(int position) {
        return concertList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) inflater.inflate(R.layout.concert_item, parent, false);

        TextView title = layoutItem.findViewById(R.id.concertTitle);
        TextView details = layoutItem.findViewById(R.id.concertDetails);
        ImageView image = layoutItem.findViewById(R.id.concertImage);
        TextView moreInfo = layoutItem.findViewById(R.id.btnMoreInfo);
        Button requests = layoutItem.findViewById(R.id.btnRequests);

        Concert concert = concertList.get(position);
        title.setText(concert.title);
        details.setText("Le " + concert.date + "\n" + concert.location);
        Picasso.get().load(concert.imageUrl).into(image);

        layoutItem.setOnClickListener( click -> {
            Log.d(TAG, "clicked on item #"+position);
            callBackActivity.onDataChange(2,position);
        });

        return layoutItem;
    }
}
