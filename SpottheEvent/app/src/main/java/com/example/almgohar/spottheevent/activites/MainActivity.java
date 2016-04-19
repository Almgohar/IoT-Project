package com.example.almgohar.spottheevent.activites;

import android.app.NotificationManager;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.almgohar.spottheevent.R;
import com.example.almgohar.spottheevent.fragments.FeedEntryFragment;
import com.example.almgohar.spottheevent.fragments.NotificationEntryFragment;
import com.example.almgohar.spottheevent.services.BeaconService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements FeedEntryFragment.OnFragmentInteractionListener, NotificationEntryFragment.OnFragmentInteractionListener {

    private static final String TAG = "BeaconReferenceApp";
    private BeaconManager beaconManager;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    MainActivity act;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().clear();
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        Intent i = new Intent(this, BeaconService.class);
        startService(i);
        act = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   beaconManager.bind(this);
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        fetchEvents("http://bef42af0.ngrok.io/IoTBackend/rest/events");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    void fetchEvents(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        request.header("Content-Type:application/json");
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                String jsonArr = response.body().string();
                Log.d(TAG,jsonArr);
                final JSONArray json = new JSONArray(jsonArr);
                Log.d("JSON", json.getJSONObject(0).toString());
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewEvents(json);
                    }
                });

                }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void viewEvents(final JSONArray events) {
        LinearLayout entriesArea = ((LinearLayout) findViewById(R.id.entries_layout));
        final ScrollView scrollView = ((ScrollView) findViewById(R.id.entries_scroll));
        entriesArea.removeAllViews();
        ((FloatingActionButton)findViewById(R.id.fab))
                .setImageDrawable(getDrawable(android.R.drawable.ic_popup_reminder));
        for (int i = 0; i < events.length(); i++) {
            FeedEntryFragment entry = null;
            try {
                Log.d("ONEJSON", events.getJSONObject(i).toString());

                        entry = FeedEntryFragment.newInstance(events.getJSONObject(i).getString("id"), events.getJSONObject(i).getString("name"),
                                events.getJSONObject(i).getString("description"),  events.getJSONObject(i).getString("imageURL"));
                } catch (JSONException e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.entries_layout, entry).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Request request = new Request.Builder()
                                    .url("http://bef42af0.ngrok.io/IoTBackend/rest/notifications")
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
                                    final JSONArray json = new JSONArray(response.body().string());
                                    act.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            viewNotifications(json);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

            }
        });

        scrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 500);

    }

    public void viewNotifications(JSONArray notifications) {
        LinearLayout entriesArea = ((LinearLayout) findViewById(R.id.entries_layout));
        final ScrollView scrollView = ((ScrollView) findViewById(R.id.entries_scroll));
        entriesArea.removeAllViews();
        ((FloatingActionButton)findViewById(R.id.fab)).setImageDrawable(getDrawable(R.drawable.ic_home_white_48dp));

        for (int i = 0; i < notifications.length(); i++) {
            NotificationEntryFragment entry = null;
            try {
                entry = NotificationEntryFragment.newInstance(notifications.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            act.getSupportFragmentManager().beginTransaction()
                    .add(R.id.entries_layout, entry).commit();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
        scrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 500);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private void sendNotification() {
        Log.d(TAG, "sending notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Booth Nearby").setContentText("7amda").setSmallIcon(R.drawable.ayb);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntent(new Intent(this, EventActivity.class));
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            builder.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.build();
            notificationManager.notify(1, builder.build());

        }



    }

