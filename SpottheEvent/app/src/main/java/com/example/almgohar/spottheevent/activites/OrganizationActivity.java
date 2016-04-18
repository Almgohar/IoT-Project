package com.example.almgohar.spottheevent.activites;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.almgohar.spottheevent.R;
import com.example.almgohar.spottheevent.fragments.FeedEntryFragment;

public class OrganizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
    }

    public void addEvents(int n) {
        final ScrollView scrollView = ((ScrollView) findViewById(R.id.entries_scrollView));
        scrollView.removeAllViews();
        for (int i = 0; i < n; i++) {
            FeedEntryFragment entry = new FeedEntryFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.entries_scrollView, entry).commit();
        }
    }
}
