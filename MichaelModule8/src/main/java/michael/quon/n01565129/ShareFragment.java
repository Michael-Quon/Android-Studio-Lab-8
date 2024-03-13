// Michael Quon N01565129
package michael.quon.n01565129;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class ShareFragment extends Fragment {

    private CheckBox checkBox;
    private EditText emailEditText, idEditText;

    // SharedPreference Key
    private static String PREFS_NAME;
    private static String PREFS_CHECKBOX;
    private static String PREFS_EMAIL;
    private static String PREFS_ID;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        String currentTime = getCurrentTimeGMT();
        String fullName = getString(R.string.name);
        String toastTime = getString(R.string.current_time_gmt) + currentTime + getString(R.string.dash) + fullName;
        Toast.makeText(requireContext(), toastTime, Toast.LENGTH_LONG).show();

        // Initialize SharedPreferences keys using string resources
        PREFS_NAME = getString(R.string.prefs_name);
        PREFS_CHECKBOX = getString(R.string.prefs_checkbox);
        PREFS_EMAIL = getString(R.string.prefs_email);
        PREFS_ID = getString(R.string.prefs_id);

        checkBox = view.findViewById(R.id.Mic_checkBox);
        emailEditText = view.findViewById(R.id.Mic_TextEmailAddress);
        idEditText = view.findViewById(R.id.Mic_NumberEdit);
        ImageButton saveButton = view.findViewById(R.id.Mic_imageButton);

        // Load user input before clearing fields
        loadUserInput();

        // clear edit text email and ID, and checkbox
        emailEditText.getText().clear();
        idEditText.getText().clear();
        checkBox.setChecked(false);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInput();
            }
        });

        return view;
    }

    private String getCurrentTimeGMT() {
        Date currentTime = new Date();

        // hh:mm:ss in GMT
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.hh_mm_ss), Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(getString(R.string.gmt)));
        return dateFormat.format(currentTime);
    }

    private void saveUserInput() {
        String email = emailEditText.getText().toString().trim();
        String id = idEditText.getText().toString().trim();

        boolean emailValid = isValidEmail(email);
        boolean idValid = !TextUtils.isEmpty(id) && id.length() >= 6;

        // Check if both email and ID are valid
        if (!emailValid || !idValid) {
            // Show error messages for invalid fields
            if (!emailValid) {
                emailEditText.setError(getString(R.string.invalid_email));
            }
            if (!idValid) {
                idEditText.setError(getString(R.string.enter_a_valid_id));
            }

            emailEditText.getText().clear();
            idEditText.getText().clear();
            emailEditText.setError(getString(R.string.invalid_email));
            idEditText.setError(getString(R.string.enter_a_valid_id));

            return; // Exit if any field is invalid
        }

        // Save user input using SharedPreferences
        SharedPreferences sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(PREFS_CHECKBOX, checkBox.isChecked());
        editor.putString(PREFS_EMAIL, email);
        editor.putString(PREFS_ID, id);
        editor.apply();

        // User input in toast
        String toastMessageBuilder = getString(R.string.email) + email +
                getString(R.string.id_) + id +
                getString(R.string.checkbox) + (checkBox.isChecked() ? getString(R.string.checked) : getString(R.string.unchecked));

        // Display the toast message
        Toast.makeText(requireContext(), toastMessageBuilder, Toast.LENGTH_LONG).show();

        // Clear fields
        clearFields();
    }


    private void clearFields() {
        emailEditText.getText().clear();
        idEditText.getText().clear();
        checkBox.setChecked(false);
        emailEditText.setError(null);
        idEditText.setError(null);
    }

    // load saved user input from SharedPreferences
    private void loadUserInput() {
        SharedPreferences sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isChecked = sharedPrefs.getBoolean(PREFS_CHECKBOX, false);
        String email = sharedPrefs.getString(PREFS_EMAIL, getString(R.string.blank));
        String id = sharedPrefs.getString(PREFS_ID, getString(R.string.blank));

        // checkbox state
        checkBox.setChecked(isChecked);

        // email and ID fields
        emailEditText.setText(email);
        idEditText.setText(id);
    }

    // check email format
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}