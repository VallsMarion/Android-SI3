package edu.polytech.concertcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.models.Request;

public class RequestAdapter extends BaseAdapter {

    private final Context context;
    private final List<Request> requestList;

    public RequestAdapter(Context context, List<Request> requestList) {
        this.context = context;
        this.requestList = requestList;
    }

    @Override
    public int getCount() {
        return requestList.size();
    }

    @Override
    public Object getItem(int position) {
        return requestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Request request = requestList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.requestTitle);
        TextView description = convertView.findViewById(R.id.requestDescription);

        title.setText(request.getConcertTitle());
        description.setText(request.getDescription());

        return convertView;
    }
}
