package com.example.almgohar.spottheevent.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    EventActivity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        act = this;
        Intent i = getIntent();
       final String id = i.getStringExtra("event_id");

                    getEventInfo(id);
        icon = (ImageView) findViewById(R.id.event_icon);
        name = (TextView) findViewById(R.id.event_name);
        organization = (TextView) findViewById(R.id.event_organization);
        location = (TextView) findViewById(R.id.event_location);
        expiry = (TextView) findViewById(R.id.event_date);
        url =  (TextView) findViewById(R.id.event_url);
        description = (TextView) findViewById(R.id.event_description);
    }

    private void getEventInfo(final String id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://bef42af0.ngrok.io/IoTBackend/rest/events/" + id)
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
                            String jsonbody = response.body().string();
                            final JSONArray json = new JSONArray(jsonbody);
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewEventInfo(json);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        thread.start();

    }

    private void viewEventInfo(JSONArray json) {
        try {
            if(json.length() > 0) {
                JSONObject jsonObject = json.getJSONObject(0);
                String name = jsonObject.getString("name");
                String icon = jsonObject.getString("imageURL");
                String org = jsonObject.getString("organization");
                String loc = jsonObject.getString("location");
                String expiry = jsonObject.getString("expirationDate");
                String description = jsonObject.getString("description");
                String url = jsonObject.getString("event_url");
                this.name.setText(name);
                this.organization.setText(org);
                this.location.setText(loc);
                this.expiry.setText(expiry);
                this.url.setText(url);
                this.description.setText(description);
            }else{
                Log.d("aaaa", "YA GAzMA");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
