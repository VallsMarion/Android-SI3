package edu.polytech.concertcare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {
    private Menuable menuable;
    private int currentActivatedIndex = 0;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        if (getArguments() != null) {
            currentActivatedIndex = getArguments().getInt(getString(R.string.index), 1) - 1;
        }

        String[] menuLabels = getResources().getStringArray(R.array.menu);

        ViewGroup itemsMenu = view.findViewById(R.id.itemsMenu);

        for (int i = 0; i < itemsMenu.getChildCount(); i++) {
            View child = itemsMenu.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout itemLayout = (LinearLayout) child;

                ImageView imageView = itemLayout.findViewById(
                        getResources().getIdentifier("menu" + (i + 1), "id", requireContext().getPackageName())
                );
                TextView textView = (TextView) itemLayout.getChildAt(1);

                if (i < menuLabels.length) {
                    textView.setText(menuLabels[i]);
                }

                if (i == currentActivatedIndex) {
                    int selectedResId = getResources().getIdentifier(
                            "menu" + (i + 1) + "_s", "drawable", requireContext().getPackageName()
                    );
                    imageView.setImageResource(selectedResId);
                }

                int finalI = i;
                imageView.setOnClickListener(menu -> {
                    ImageView oldImageView = itemsMenu.findViewById(
                            getResources().getIdentifier("menu" + (currentActivatedIndex + 1), "id", requireContext().getPackageName())
                    );
                    int oldResId = getResources().getIdentifier(
                            "menu" + (currentActivatedIndex + 1), "drawable", requireContext().getPackageName()
                    );
                    oldImageView.setImageResource(oldResId);

                    // Mettre à jour l'index
                    currentActivatedIndex = finalI;

                    int newResId = getResources().getIdentifier(
                            "menu" + (currentActivatedIndex + 1) + "_s", "drawable", requireContext().getPackageName()
                    );
                    imageView.setImageResource(newResId);

                    menuable.onMenuChange(currentActivatedIndex);
                });
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Menuable) {
            menuable = (Menuable) requireActivity();
        } else {
            throw new AssertionError("Classe " + requireActivity().getClass().getName() + " ne met pas en œuvre Menuable.");
        }
    }
}
