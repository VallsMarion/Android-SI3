package edu.polytech.concertcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<Request> requestList;

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView deleteIcon;

        public RequestViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.requestTitle);
            description = view.findViewById(R.id.requestDescription);
            deleteIcon = view.findViewById(R.id.deleteIcon);
        }
    }

    public RequestAdapter(List<Request> requests) {
        this.requestList = requests;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.title.setText(request.getTitle());
        holder.description.setText(request.getDescription());

        holder.deleteIcon.setOnClickListener(v -> {
            requestList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
