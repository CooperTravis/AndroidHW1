package com.example.weatherapplication;

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
import java.net.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String GOOGLEURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String GEOAPIKEY = "&key=AIzaSyA2kem7Tpl-bbyA0UiHAJIj4YSJGtf6Rew";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        new SubmitAddressTask().execute();
    }

    private class SubmitAddressTask extends AsyncTask{

        @Override
        protected String doInBackground(Object[] objects) {
            Double longitude = 0.0;
            Double latitude = 0.0;

            String address;

            EditText showAddress = (EditText) findViewById(R.id.editText);

            address = showAddress.getText().toString();

            address.replaceAll(" ", "+");

            String link = GOOGLEURL + address + GEOAPIKEY;

            String jsonString = null;

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

                System.out.println(jsonGeometry.toString());

                JSONObject jsonLocation = jsonGeometry.getJSONObject("location");

                longitude = jsonLocation.getDouble("lng");

                latitude = jsonLocation.getDouble("lat");



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Need to figure out where to pass this, FINAL variables?
            return longitude.toString() + ", " + latitude.toString();

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


}
