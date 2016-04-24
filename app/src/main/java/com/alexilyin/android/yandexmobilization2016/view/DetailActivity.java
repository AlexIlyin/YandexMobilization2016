package com.alexilyin.android.yandexmobilization2016.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.R;
import com.alexilyin.android.yandexmobilization2016.utils.Misc;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    @Inject
    protected ArtistHelper artistHelper;
    @Inject
    protected Misc misc;
    @Inject
    protected Picasso picasso;

    public static final String PLAY_URL = "https://music.yandex.ru/iframe/#playlist/artist/";

    private Long artistId;
    private static final String INTENT_NAME = "DetailActivity";
    private ImageView toolbarCover;
    private TextView genre;
    private TextView albums;
    private TextView tracks;
    private TextView description;
    private FloatingActionButton fab;
    private boolean fabIsShown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            toolbar.setTransitionName("name_transition");
        }


        toolbarCover = (ImageView) findViewById(R.id.toolbar_image);
        genre = (TextView) findViewById(R.id.detail_genre);
        albums = (TextView) findViewById(R.id.detail_albums);
        tracks = (TextView) findViewById(R.id.detail_tracks);
        description = (TextView) findViewById(R.id.detail_description);
        artistId = getIntent().getLongExtra(INTENT_NAME, -1);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (v.isShown() != fabIsShown) {
                    fabIsShown = v.isShown();
                    invalidateOptionsMenu();
                }
            }
        });

        updateViewData();

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_play).setVisible(!fabIsShown);
        return super.onPrepareOptionsMenu(menu);
    }

    static Intent prepareIntent(Context context, long id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_NAME, id);
        return intent;
    }


    private void updateViewData() {
        Cursor cursor = artistHelper.query(artistId);

        String url = artistHelper.getBigCover(cursor);

        postponeEnterTransition();
        picasso.with(this).load(url)
                .placeholder(R.drawable.placeholder)
                .noFade()
                .fit()
                .centerInside()
                .into(toolbarCover, new Callback() {
                    @Override
                    public void onSuccess() {
                        continueAnimation();
                    }

                    @Override
                    public void onError() {
                        continueAnimation();
                    }
                });

        animationHandler.sendMessageDelayed(Message.obtain(), 700);

        genre.setText(misc.buildGenreString(cursor));
        albums.setText(artistHelper.getAlbums(cursor).toString());
        tracks.setText(artistHelper.getTracks(cursor).toString());
        description.setText(artistHelper.getDescription(cursor));
        setTitle(artistHelper.getName(cursor));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
    }

    private void play() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(PLAY_URL + artistId));
        startActivity(intent);
    }

    private void continueAnimation() {
        animationHandler.removeCallbacksAndMessages(null);
        animationHandler.handleMessage(Message.obtain());
    }

    Handler animationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_play) {
            play();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
