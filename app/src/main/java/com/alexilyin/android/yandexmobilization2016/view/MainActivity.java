package com.alexilyin.android.yandexmobilization2016.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.R;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;
import com.alexilyin.android.yandexmobilization2016.rest.YandexService;
import com.alexilyin.android.yandexmobilization2016.utils.Misc;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public static final int PREFERRED_CARD_WIDTH = 350;

    @Inject
    protected YandexService yandexService;

    @Inject
    protected ArtistHelper artistHelper;


    private ArtistRecyclerViewAdapter artistAdapter;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float displayWidthDp = metrics.widthPixels / metrics.density;
        int columns = Math.round(displayWidthDp / PREFERRED_CARD_WIDTH);

        Cursor artistCursor = artistHelper.query();
        artistAdapter = new ArtistRecyclerViewAdapter(this, artistCursor);
        RecyclerView artistRecyclerView = (RecyclerView) findViewById(R.id.artist_recyclerview);
        if (artistRecyclerView != null) {
            artistRecyclerView.setLayoutManager(new GridLayoutManager(this,columns));
            artistRecyclerView.setAdapter(artistAdapter);

        }


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateViewData();
            }
        };
        intentFilter = new IntentFilter(Misc.DATA_UPDATED_ACTION);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        showProgressBar(artistCursor);

    }

    private void showProgressBar(Cursor cursor) {
        if (cursor.getCount() == 0) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateViewData();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void updateViewData() {
        Cursor artistCursor = artistHelper.query();
        showProgressBar(artistCursor);
        artistAdapter.changeCursor(artistCursor);
    }

}
