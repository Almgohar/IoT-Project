package com.example.almgohar.spottheevent.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almgohar.spottheevent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    ImageView icon;
    TextView name;
    TextView organization;
    TextView location;
    TextView expiry;
    TextView url;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Intent i = getIntent();
       final String id = i.getStringExtra("event_id");
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    getEventInfo(id);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        icon = (ImageView) findViewById(R.id.event_icon);
        name = (TextView) findViewById(R.id.event_name);
        organization = (TextView) findViewById(R.id.event_organization);
        location = (TextView) findViewById(R.id.event_location);
        expiry = (TextView) findViewById(R.id.event_date);
        url =  (TextView) findViewById(R.id.event_url);
        description = (TextView) findViewById(R.id.event_description);
    }

    private void getEventInfo(String id) {
        Request request = new Request.Builder()
                .url("http://private-2ef29-semanticiot.apiary-mock.com/events/{id}")
                .build();
        request.header("Content-Type:application/json");
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            try {
                JSONObject json = new JSONObject(response.body().string());
                viewEventInfo(json);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewEventInfo(JSONObject json) {
        try {
            String name = json.getString("name");
            String icon = json.getString("imageURL");
            String org = json.getString("organization");
            String loc = json.getString("location");
            String expiry = json.getString("expirationDate");
            String description = json.getString("description");
            String url = json.getString("event_url");

            this.name.setText(name);
            this.organization.setText(org);
            this.location.setText(loc);
            this.expiry.setText(expiry);
            this.url.setText(url);
            this.description.setText(description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
