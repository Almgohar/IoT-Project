package com.example.almgohar.spottheevent.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.almgohar.spottheevent.R;
import com.example.almgohar.spottheevent.activites.EventActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BeaconService extends Service implements BeaconConsumer {
    private BeaconManager beaconManager;
    private static final String TAG = "BeaconReferenceApp";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public BeaconService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            //beaconManager.startRangingBeaconsInRegion(new Region());
        } catch (RemoteException e) {
        }


        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    String distance = beacons.iterator().next().getDistance() + " meters away.";
                    final String major = beacons.iterator().next().getId2() + "";
                    final String minor = beacons.iterator().next().getId3() + "";
                    sendNotification();
                    Log.d(TAG, major);
                    Log.d(TAG, minor);


                }
            }
        });
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
    }

    private void sendNotification() {
        Log.d(TAG, "sending notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("AAAA").setContentText("You just passed the booth of the event " + "AYB").setSmallIcon(R.drawable.ayb);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent i = new Intent(this, EventActivity.class);
        //        i.putExtra("event_id", id);
        stackBuilder.addNextIntent(i);
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
