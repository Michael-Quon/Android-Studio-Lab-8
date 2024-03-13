// Michael Quon N01565129
package michael.quon.n01565129;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    private TextView sharedCheckBox, sharedEmail, sharedID;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        sharedCheckBox = view.findViewById(R.id.Mic_shared_CheckBox);
        sharedEmail = view.findViewById(R.id.Mic_shared_Email);
        sharedID = view.findViewById(R.id.Mic_shared_ID);

        clearTextViews();

        TextView firstNameTV = view.findViewById(R.id.Mic_firstName);
        TextView lastNameTV = view.findViewById(R.id.Mic_lastName);
        ToggleButton orientationToggle = view.findViewById(R.id.Mic_orientationToggleButton);

        // toast with counter and name
        int counter = getCounter();
        String fullName = getResources().getString(R.string.name);
        Toast.makeText(getActivity(), getString(R.string.counter) + counter + getString(R.string.name_toast) + fullName, Toast.LENGTH_SHORT).show();

        firstNameTV.setText(getResources().getString(R.string.michael));
        lastNameTV.setText(getResources().getString(R.string.quon));

        displaySharedPrefData();

        orientationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        });
        return view;
    }

    private void clearTextViews() {
        sharedCheckBox.setText(getString(R.string.no_data));
        sharedEmail.setText(getString(R.string.no_data));
        sharedID.setText(getString(R.string.no_data));
    }

    private int getCounter() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.counterprefs), Context.MODE_PRIVATE);
        int counter = sharedPreferences.getInt(getString(R.string.counter), 0);
        counter++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.counter), counter);
        editor.apply();
        return counter;
    }

    @SuppressLint("SetTextI18n")
    private void displaySharedPrefData() {
        // Get shared preferences data from ShareFragment
        SharedPreferences sharedPrefs = requireActivity().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE);
        boolean isChecked = sharedPrefs.getBoolean(getString(R.string.prefs_checkbox), false);
        String email = sharedPrefs.getString(getString(R.string.prefs_email), getResources().getString(R.string.no_data));
        String id = sharedPrefs.getString(getString(R.string.prefs_id), getResources().getString(R.string.no_data));

        sharedCheckBox.setText(getString(R.string.checkboxchecked) + isChecked);
        sharedEmail.setText(getString(R.string.email) + email);
        sharedID.setText(getString(R.string.id_) + id);
    }
}