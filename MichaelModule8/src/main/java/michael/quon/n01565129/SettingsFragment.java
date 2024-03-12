// Michael Quon N01565129
package michael.quon.n01565129;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private ImageView imageView; // ImageView to display selected image
    private boolean isPermissionDeniedOnce = false; // Flag to track if permission was denied once

    // ActivityResultLauncher, requesting permission to access photos
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(requireContext(), getString(R.string.permission_allowed), Toast.LENGTH_SHORT).show();
                    // Permission granted, proceed to access photos
                    accessPhotos();
                } else {
                    // Permission denied and it's the first time, show a toast
                    if (!isPermissionDeniedOnce) {
                        Toast.makeText(requireContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                        isPermissionDeniedOnce = true;
                    } else {
                        // Permission denied again, open app settings
                        openAppSettings();
                    }
                }
            });

    // ActivityResultLauncher, capturing image selection result
    private final ActivityResultLauncher<Intent> getImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // Image selection successful and data is available
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        // Set the selected image to the ImageView
                        imageView.setImageURI(imageUri);
                    }
                }
            });

    // Constructor
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        imageView = view.findViewById(R.id.Mic_imageView2); // Initialize ImageView
        // Button to access photos
        Button accessPhotosButton = view.findViewById(R.id.Mic_accessPhotosButton); // Initialize Button

        // Set click listener for the access photos button
        accessPhotosButton.setOnClickListener(v -> requestPermission());

        return view;
    }

    // Request permission to access photos
    private void requestPermission() {
        // Request permission to read external storage
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    // Access photos from the device
    private void accessPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start activity for selecting images
        getImageLauncher.launch(intent);
    }

    // Open app settings
    private void openAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(getString(R.string.pckg), requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
