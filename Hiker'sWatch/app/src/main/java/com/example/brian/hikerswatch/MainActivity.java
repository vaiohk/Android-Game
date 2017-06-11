package com.example.brian.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check is the permssion grant
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startlistening();

        }
    }

    public void startlistening(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }

    }
    public void updateLocationInfo(Location location){
        Log.i("LocationInfo", location.toString());

        TextView latTextView = (TextView) findViewById(R.id.LatitudetextView);
        TextView longTextView = (TextView) findViewById(R.id.LongtitudeView);
        TextView altTextView = (TextView) findViewById(R.id.AltitudetextView);
        TextView accTextView = (TextView) findViewById(R.id.acctextView);


        latTextView.setText("Latitude: " + location.getLatitude());
        longTextView.setText("Longtitude: " + location.getLongitude());
        altTextView.setText("Altitude: " + location.getAltitude());
        accTextView.setText("Accuracy: " + location.getAccuracy());

        //Get Address
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            String address = "Could not find address";

            List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (listAddress!= null && listAddress.size() > 0){
                Log.i("PlaceInfo", listAddress.get(0).toString());

                address= "Address: \n" ;

                if(listAddress.get(0).getSubThoroughfare() != null){
                    address += listAddress.get(0).getSubThoroughfare() + " ";
                }

                if(listAddress.get(0).getThoroughfare() != null){
                    address += listAddress.get(0).getThoroughfare() +"\n";
                }

                if(listAddress.get(0).getLocality() != null){
                    address += listAddress.get(0).getLocality() +"\n";
                }

                if(listAddress.get(0).getPostalCode() != null){
                    address += listAddress.get(0).getPostalCode() +"\n";
                }

                if(listAddress.get(0).getCountryName() != null){
                    address += listAddress.get(0).getCountryName() +"\n";
                }
            }

            TextView addressTextView = (TextView) findViewById(R.id.addresstextView);
            addressTextView.setText(address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Check the android build
        if (Build.VERSION.SDK_INT < 23){
            startlistening();

        }else
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //check there isn't a last location
                if (location!= null){
                    updateLocationInfo(location);
                }
            }
        }
    }
}
