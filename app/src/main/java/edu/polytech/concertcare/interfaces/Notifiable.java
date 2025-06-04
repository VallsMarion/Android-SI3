package edu.polytech.concertcare.interfaces;

import android.content.Context;

public interface Notifiable {
    void onClick(int numFragment);
    void onDataChange(int numFragment, Object object);

    Context getContext();

}
