// Michael Quon N01565129
package michael.quon.n01565129;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private ImageView imageView;
    private final int[] imageResource = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.squritle, R.drawable.pikachu};
    private int imageIndex = 0;
    private int clicks = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        // initialize elements
        TextView textView = view.findViewById(R.id.Mic_textView);
        Button button = view.findViewById(R.id.Mic_button);
        imageView = view.findViewById(R.id.Mic_imageView);

        // set default image
        imageView.setImageResource(imageResource[imageIndex]);

        // set click listner for button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change image on click
                imageIndex = (imageIndex + 1) % imageResource.length;
                imageView.setImageResource(imageResource[imageIndex]);

                // snackbar
                Snackbar.make(view, getString(R.string.name_id_clicks) + clicks++, Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}