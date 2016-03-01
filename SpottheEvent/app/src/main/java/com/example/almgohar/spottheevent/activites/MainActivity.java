package com.example.almgohar.spottheevent.activites;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.almgohar.spottheevent.R;
import com.example.almgohar.spottheevent.fragments.FeedEntryFragment;
import com.example.almgohar.spottheevent.fragments.NotificationEntryFragment;

public class MainActivity extends AppCompatActivity implements FeedEntryFragment.OnFragmentInteractionListener, NotificationEntryFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewEntries(10);
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
