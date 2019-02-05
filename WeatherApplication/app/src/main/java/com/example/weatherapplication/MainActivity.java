package com.example.weatherapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private static final String GOOGLEURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String GEOAPIKEY = "&key=AIzaSyA2kem7Tpl-bbyA0UiHAJIj4YSJGtf6Rew";
    private static final String DARKSKYAPI = "fb0688b2fb0335c0d97f904484a677ca";

    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String TEMPERATURE = "temperature";
    private static final String WINDSPEED = "windspeed";
    private static final String HUMIDITY = "humidity";
    private static final String PRECIPITATION = "precipitation";
    private static final String ARRAY = "array";

    public static ArrayList<Double> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        try {
            info = (ArrayList<Double>) new SubmitAddressTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        newScreen(view);
    }


    private class SubmitAddressTask extends AsyncTask{

        @Override
        protected ArrayList<Double> doInBackground(Object[] objects) {
            Double longitude = 0.0;
            Double latitude = 0.0;
            Double temperature = 0.0;
            Double windSpeed = 0.0;
            Double humidity = 0.0;
            Double precipitation = 0.0;

            ArrayList<Double> info = new ArrayList<>();

            String address;

            EditText showAddress = (EditText) findViewById(R.id.editText);

            address = showAddress.getText().toString();

            address.replaceAll(" ", "+");

            String link = GOOGLEURL + address + GEOAPIKEY;

            String darklink = null;

            String jsonString = null;

            String jsonString1 = null;

            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                jsonString = convertStringtoJSON(in);

                //System.out.println(jsonString);

                JSONObject json = new JSONObject(jsonString);


                JSONArray jsonArray = json.getJSONArray("results");

                //System.out.println(jsonArray.toString());



                JSONObject jPlace = jsonArray.getJSONObject(0);

                JSONObject jsonGeometry = jPlace.getJSONObject("geometry");

                JSONObject jsonLocation = jsonGeometry.getJSONObject("location");

                longitude = jsonLocation.getDouble("lng");

                latitude = jsonLocation.getDouble("lat");

                conn.disconnect();

                darklink = "https://api.darksky.net/forecast/" + DARKSKYAPI + "/" + latitude + "," + longitude;

                URL url1 = new URL(darklink);
                HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                conn.setRequestMethod("GET");

                InputStream in1 = new BufferedInputStream(conn1.getInputStream());
                jsonString1 = convertStringtoJSON(in1);

                JSONObject json1 = new JSONObject(jsonString1);

                JSONObject currently = json1.getJSONObject("currently");

                temperature = currently.getDouble("temperature");
                precipitation = currently.getDouble("precipProbability");
                windSpeed = currently.getDouble("windSpeed");
                humidity = currently.getDouble("humidity");

                conn1.disconnect();

                // Need to push this to next activity






            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            info.add(longitude);
            info.add(latitude);
            info.add(temperature);
            info.add(windSpeed);
            info.add(humidity);
            info.add(precipitation);

            return info;





        }

    }



    public String convertStringtoJSON(InputStream in){
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();

        String line;
        try {
            while ((line = bf.readLine())!= null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result.toString();
    }

    public void newScreen(View view){

        Intent mapView = new Intent(this, Main2Activity.class);

        mapView.putExtra(LONGITUDE, info.get(0));
        mapView.putExtra(LATITUDE, info.get(1));
        mapView.putExtra(TEMPERATURE, info.get(2));
        mapView.putExtra(WINDSPEED, info.get(3));
        mapView.putExtra(HUMIDITY, info.get(4));
        mapView.putExtra(PRECIPITATION, info.get(5));


        startActivity(mapView);

    }


}
