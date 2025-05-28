package edu.polytech.concertcare.concerts;

import android.content.Context;

/**
 * Interface pour écouter les évènements sur le nom du diplome
 */

    public interface Clickable {
        void onClicItem(int itemIndex);
        Context getContext();

}
