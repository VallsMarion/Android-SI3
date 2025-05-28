package edu.polytech.concertcare.concerts;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;


import com.squareup.picasso.Picasso;

import edu.polytech.concertcare.Notifiable;
import edu.polytech.concertcare.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConcertItemFragment extends Fragment {
    private final String TAG = "frallo " + getClass().getSimpleName();
    private Notifiable notifiable;

    private Concert concert;

    public ConcertItemFragment() {
        // Required empty public constructor
    }



    private void changeViewSize(View view, int width, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        LinearLayout.LayoutParams constraintParams = (LinearLayout.LayoutParams) params;
        constraintParams.width = (int) (width * notifiable.getContext().getResources().getDisplayMetrics().density);
        constraintParams.height = (int) (height * notifiable.getContext().getResources().getDisplayMetrics().density);
        view.setLayoutParams(constraintParams);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int index = getArguments().getInt("concert_index");
             concert = ConcertList.getConcerts().get(index);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
            Log.d(TAG, "Class " + requireActivity().getClass().getSimpleName() + " implements Notifiable.");
        } else {
            throw new AssertionError("Classe " + requireActivity().getClass().getName() + " ne met pas en œuvre Notifiable.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutItem = inflater.inflate(R.layout.fragment_concert_item, container, false);
        View mainContainer = layoutItem.findViewById(R.id.mainContainer);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fragment_enter);
        mainContainer.startAnimation(animation);
        //((TextView)layoutItem.findViewById(R.id.name)).setText(valorantCharacter.getName());
        //((ImageView)layoutItem.findViewById(R.id.characterImage)).setImageResource(valorantCharacter.getPicture());
        //Picasso.get().load(valorantCharacter.getPictureFace()).into(((ImageView)layoutItem.findViewById(R.id.characterImage)));
        //((TextView)layoutItem.findViewById(R.id.)).setText(valorantCharacter.getDescription());
        TextView title = layoutItem.findViewById(R.id.concertTitle);
        TextView date = layoutItem.findViewById(R.id.concertDate);
        TextView location = layoutItem.findViewById(R.id.concertLocation);
        ImageView image = layoutItem.findViewById(R.id.concertImage);
        View rootLayout = layoutItem.findViewById(R.id.topGradientContainer);

        // Get concert index from arguments
        if (getArguments() != null) {
            int index = getArguments().getInt("concert_index", -1);
            if (index != -1 && index < ConcertList.getConcerts().size()) {
                Concert concert = ConcertList.getConcerts().get(index);

                title.setText(concert.title);
                date.setText("Date: " + concert.date);
                location.setText("Location: " + concert.location);
                //the following is commented because we did not have time to finish implementation
                /* It extracts dominant colors for each concerts image and applies a gradient background to it
                 */
                /*Picasso.get()
                        .load(concert.imageUrl)
                        .into(new com.squareup.picasso.Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                image.setImageBitmap(bitmap); // still sets image

                                // Extract dominant and dark vibrant colors
                                Palette.from(bitmap).generate(palette -> {
                                    int fallback = Color.parseColor("#FFA726"); // bright orange fallback

                                    int lightVibrant = palette.getLightVibrantColor(fallback);
                                    int darkVibrant = palette.getDarkVibrantColor(fallback);

                                    // Add highlight to the top color (glossier look)
                                    int glossyTop = lightenColor(lightVibrant, 0.3f);
                                    int richBottom = darkenColor(darkVibrant, 0.2f);

                                    applyGradientBackground(rootLayout, glossyTop, richBottom);
                                });


                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }
                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {}
                        });*/

                Picasso.get().load(concert.imageUrl).into(image);
                //If we want to add button that redirects to staff map for the concert in question
                /*Button showOnMap = layoutItem.findViewById(R.id.btnShowOnMap);
                showOnMap.setOnClickListener(v -> {
                    if (getArguments() != null) {
                        int concertIndex = getArguments().getInt("concert_index", -1);
                        if (concertIndex != -1) {
                            // Notify ControlActivity
                            if (requireActivity() instanceof Notifiable) {
                                ((Notifiable) requireActivity()).onDataChange(1000, concertIndex);
                            }
                        }
                    }
                });*/
            }
        }

        return layoutItem;
    }
    /*The following methods are commented because they were meant to upgrade design:
    **add gradient background to the concert item view
    ** but didn't have time to finish the new design
     */
    /*private void applyGradientBackground(View root, int startColor, int endColor) {
            GradientDrawable gradient = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{lightenColor(startColor, 0.3f), endColor, Color.WHITE}

            );
            gradient.setCornerRadius(0f);
            gradient.setCornerRadii(new float[]{
                0, 0,   // top-left
                0, 0,   // top-right
                50, 50, // bottom-right
                50, 50  // bottom-left
            });
            root.setBackground(gradient);
    }*/

    /*private int lightenColor(int color, float factor) {
        int r = (int) ((Color.red(color) * (1 - factor) / 255f + factor) * 255);
        int g = (int) ((Color.green(color) * (1 - factor) / 255f + factor) * 255);
        int b = (int) ((Color.blue(color) * (1 - factor) / 255f + factor) * 255);
        return Color.rgb(r, g, b);
    }

    private int darkenColor(int color, float factor) {
        int r = (int) (Color.red(color) * (1 - factor));
        int g = (int) (Color.green(color) * (1 - factor));
        int b = (int) (Color.blue(color) * (1 - factor));
        return Color.rgb(r, g, b);
    }*/


}