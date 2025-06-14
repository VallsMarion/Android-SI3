package edu.polytech.concertcare.views;

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
import androidx.lifecycle.ViewModelProvider;


import com.squareup.picasso.Picasso;

import java.util.List;

import edu.polytech.concertcare.viewmodels.ConcertList;
import edu.polytech.concertcare.viewmodels.ConcertViewModel;
import edu.polytech.concertcare.interfaces.Notifiable;
import edu.polytech.concertcare.R;
import edu.polytech.concertcare.models.Concert;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConcertItemFragment extends Fragment {
    private final String TAG = "frallo " + getClass().getSimpleName();
    private Notifiable notifiable;

    private String idConcert;

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
            idConcert = getArguments().getString("concert_id");
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

        ConcertViewModel viewModel = new ViewModelProvider(requireActivity()).get(ConcertViewModel.class);
        viewModel.getConcerts().observe(getViewLifecycleOwner(), concerts -> {
            if (concerts != null) {
                List<Concert> concertList = ConcertList.getConcerts();
                Concert concert = concertList .stream().filter(e-> e.id == idConcert).findFirst().orElse(null);
                if (concert != null) {
                    title.setText(concert.title);
                    date.setText("Date: " + concert.date);
                    location.setText("Location: " + concert.location);

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
                } else {
                    Log.e(TAG, "Concert with id " + idConcert + " not found.");
                }
            }
        });

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