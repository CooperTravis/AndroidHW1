package com.example.weatherapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private static final String ARRAY = "array";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String TEMPERATURE = "temperature";
    private static final String WINDSPEED = "windspeed";
    private static final String HUMIDITY = "humidity";
    private static final String PRECIPITATION = "precipitation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showMap(){
        Double lgt = getIntent().getDoubleExtra(LONGITUDE, 0.0);
        Double lat = getIntent().getDoubleExtra(LATITUDE, 0.0);
        Double temp = getIntent().getDoubleExtra(TEMPERATURE, 0.0);
        Double wind = getIntent().getDoubleExtra(WINDSPEED, 0.0);
        Double hum = getIntent().getDoubleExtra(HUMIDITY, 0.0);
        Double pre = getIntent().getDoubleExtra(PRECIPITATION, 0.0);
        
    }

}
