package com.alexilyin.android.yandexmobilization2016.db;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.rest.JSONDataObject;
import com.alexilyin.android.yandexmobilization2016.rest.YandexService;
import com.alexilyin.android.yandexmobilization2016.utils.Misc;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;


public class DownloadService extends IntentService {
    public DownloadService() {
        super("DownloadService");
    }

    @Inject
    protected ArtistHelper artistHelper;
    @Inject
    protected ArtistGenreHelper artistGenreHelper;
    @Inject
    protected GenreHelper genreHelper;
    @Inject
    protected YandexService yandexService;

    @Inject
    protected DBOpenHelper dbOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        ((MyApplication) getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (isConnectedToNetwork()) {

            try {

                List<JSONDataObject> data = requestArtistData();


                if (data != null && !data.isEmpty()) {

                    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    try {
                        db.beginTransaction();

                        db.execSQL(DBContract.ArtistTable.SQL_QUERY_DROP_TABLE);
                        db.execSQL(DBContract.GenreTable.SQL_QUERY_DROP_TABLE);
                        db.execSQL(DBContract.ArtistGenreTable.SQL_QUERY_DROP_TABLE);

                        db.execSQL(DBContract.ArtistTable.SQL_QUERY_CREATE_TABLE);
                        db.execSQL(DBContract.GenreTable.SQL_QUERY_CREATE_TABLE);
                        db.execSQL(DBContract.ArtistGenreTable.SQL_QUERY_CREATE_TABLE);

                        genreHelper.insert(db, data);
                        artistHelper.insert(db, data);
                        artistGenreHelper.insert(db, data);

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                        notifyChanges();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Check network connection before attempting to download new data
    private boolean isConnectedToNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    // Request Artist data via YandexService
    @Nullable
    private List<JSONDataObject> requestArtistData() throws IOException {
        return yandexService.getArtistsData()
                .execute()
                .body();
    }

    // Notify List Activity about DB changes
    private void notifyChanges() {
        Intent localIntent = new Intent(Misc.DATA_UPDATED_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

}
