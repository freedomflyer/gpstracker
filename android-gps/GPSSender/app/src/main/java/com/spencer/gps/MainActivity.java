package com.spencer.gps;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity{
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    LocationManager mlocManager = null;
    LocationListener mlocListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        // Send location every 1.5 seconds
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getAndSendLocation();
                    }
                });
            }
        }, 0, 1500);
    }

    // Retrieves location coordinates and POSTs them to server
    public void getAndSendLocation() {

        final TextView latitudeField = (TextView)  findViewById(R.id.lat);
        final TextView longitudeField = (TextView) findViewById(R.id.longit);

        // Set textfields
        if (mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            latitudeField.setText("Latitude: " + MyLocationListener.latitude + '\n');
            longitudeField.setText("Longitude: " + MyLocationListener.longitude + '\n');

            // Send POST
            new doPost().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MyLocationListener.latitude, MyLocationListener.longitude);

        } else {
            latitudeField.setText("GPS is not turned on...");
            longitudeField.setText("GPS is not turned on...");
        }
    }


    // POSTs data to server
    public class doPost extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            double lat = (Double) objects[0];
            double longit = (Double) objects[1];

            HttpClient httpclient = new DefaultHttpClient();
            // HttpPost httppost = new HttpPost("http://192.168.1.110:3000/coord");
            HttpPost httppost = new HttpPost("http://frozen-falls-2431.herokuapp.com/coord");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(lat)));
                nameValuePairs.add(new BasicNameValuePair("long", Double.toString(longit)));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }

            return null;
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();

    }
} 