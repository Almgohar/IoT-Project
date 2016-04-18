package com.example.almgohar.spottheevent.activites;

import android.app.NotificationManager;

import android.content.Context;
import android.net.Uri;
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

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.json.JSONArray;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, FeedEntryFragment.OnFragmentInteractionListener, NotificationEntryFragment.OnFragmentInteractionListener {

    private static final String TAG = "BeaconReferenceApp";
    private BeaconManager beaconManager;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewEntries(10);
        beaconManager.bind(this);

            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Log.d("llalaal", fetchEvents("http://polls.apiblueprint.org/events"));
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
        beaconManager.unbind(this);
    }

    String fetchEvents(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        request.header("Content-Type:application/json");
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        }
        catch (RemoteException e) {    }
    }

    public void viewEntries(int length) {
        LinearLayout entriesArea = ((LinearLayout) findViewById(R.id.entries_layout));
        final ScrollView scrollView = ((ScrollView) findViewById(R.id.entries_scroll));
        entriesArea.removeAllViews();
        ((FloatingActionButton)findViewById(R.id.fab)).setImageDrawable(getDrawable(android.R.drawable.ic_popup_reminder));
        for (int i = 0; i < length; i++) {
            FeedEntryFragment entry = new FeedEntryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.entries_layout, entry).commit();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                viewNotifications(10);
            }
        });

        scrollView.postDelayed(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 500);

    }

    public void viewNotifications(int length) {
        LinearLayout entriesArea = ((LinearLayout) findViewById(R.id.entries_layout));
        final ScrollView scrollView = ((ScrollView) findViewById(R.id.entries_scroll));
        entriesArea.removeAllViews();
        ((FloatingActionButton)findViewById(R.id.fab)).setImageDrawable(getDrawable(R.drawable.ic_home_white_48dp));
        for (int i = 0; i < length; i++) {
            NotificationEntryFragment notification = new NotificationEntryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.entries_layout, notification).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                viewEntries(10);
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

}
