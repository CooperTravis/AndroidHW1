package com.example.weatherapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String ARRAY = "array";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String TEMPERATURE = "temperature";
    private static final String WINDSPEED = "windspeed";
    private static final String HUMIDITY = "humidity";
    private static final String PRECIPITATION = "precipitation";

    private MapView mapView;
    private GoogleMap gmap;
    Double lgt;
    Double lat;
    Double temp;
    Double wind;
    Double hum;
    Double pre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);

    }

    public void showMap(){




        // David Driving

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;

        lgt = getIntent().getDoubleExtra(LONGITUDE, 0.0);
        lat = getIntent().getDoubleExtra(LATITUDE, 0.0);
        temp = getIntent().getDoubleExtra(TEMPERATURE, 0.0);
        wind = getIntent().getDoubleExtra(WINDSPEED, 0.0);
        hum = getIntent().getDoubleExtra(HUMIDITY, 0.0);
        pre = getIntent().getDoubleExtra(PRECIPITATION, 0.0);

        LatLng location = new LatLng(lat, lgt);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 16);
        gmap.addMarker(new MarkerOptions().position(location).title("Location"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(location));
        gmap.animateCamera(cameraUpdate);



    }
}
