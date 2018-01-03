package com.example.android.hickersguide;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {
    LocationManager locationManager;
    String provider;

    TextView latTV;
    TextView lngTV;
    TextView accuracyTV;
    TextView speedTV;
    TextView bearingTV;
    TextView altitudeTV;
    TextView addressTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latTV = (TextView) findViewById(R.id.lat);
        lngTV = (TextView) findViewById(R.id.lng);
        accuracyTV = (TextView) findViewById(R.id.accuracy);
        speedTV = (TextView) findViewById(R.id.speed);
        bearingTV = (TextView) findViewById(R.id.bearing);
        altitudeTV = (TextView) findViewById(R.id.altitude);
        addressTV = (TextView) findViewById(R.id.address);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        Double altitude = location.getAltitude();
        Float bearing = location.getBearing();
        Float speed = location.getSpeed();
        Float accuracy = location.getAccuracy();

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lat, lng, 1);
            if (listAddresses != null && listAddresses.size() > 0) {
                Log.i("PlaceInfo", listAddresses.get(0).toString());

                String addressHolder = "";
                for (int i = 0; i <= listAddresses.get(0).getMaxAddressLineIndex(); i++) {
                    addressHolder = listAddresses.get(0).getAddressLine(i) + "\n";
                }
                addressTV.setText("Address\n " + addressHolder.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latTV.setText("Latitude: " + lat.toString());
        lngTV.setText("Longitude: " + lng.toString());
        accuracyTV.setText("Accuracy: " + accuracy.toString() + "m");
        speedTV.setText("Speed: " + speed.toString() + "m/s");
        bearingTV.setText("Bearing: " + bearing.toString());
        altitudeTV.setText("Altitude: " + altitude.toString());


        Log.i("The Latitude", String.valueOf(lat));
        Log.i("The Longitude", String.valueOf(lng));
        Log.i("The Altitude", String.valueOf(altitude));
        Log.i("The Bearing", String.valueOf(bearing));
        Log.i("The Speed", String.valueOf(speed));
        Log.i("The Accuracy", String.valueOf(accuracy));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
