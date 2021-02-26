package com.example.flickrapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * The main activity
 * Allow to get a random tree picture,
 * a picture taken from a near location or
 * to go to an image list activity
 */
public class MainActivity extends AppCompatActivity implements LocationListener {
    // View element references
    Button getImageButton, getLocationImageButton, listButton;
    TextView getLocationText;
    ImageView image;

    // Location attributes
    LocationManager locationManager;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.image);

        // Prepare location related actions & views
        setUpLocationListener();
        getLocationText = findViewById(R.id.getLocationText);
        getLocationImageButton = findViewById(R.id.getLocationImageButton);
        getLocationImageButton.setOnClickListener(new GetLocationImageOnClickListener(image, this));

        // Prepare the Get Image Button
        getImageButton = findViewById(R.id.getImageButton);
        getImageButton.setOnClickListener(new GetImageOnClickListener(image));

        // Prepare the Image List button
        listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new ListActvity
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

    /**
     * @return The last known latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The last known longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Ask permission to use geo localisation and
     * set up the location manager
     */
    private void setUpLocationListener() {
        // Set a default location
        latitude = 48.8;
        longitude = 2.4;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // If the app doesn't have the required permissions, ask the user for them
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }

        // Start checking the location
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    /**
     * Update the latitude & longitude
     * Update the GUI location display
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.i("Position", "lat : " + latitude + "| lon : " + longitude);
        // Update the GUI location display
        getLocationText.setText("lat : " + String.format("%.3f", latitude) + " | lon : " + String.format("%.3f", longitude));
    }
}
