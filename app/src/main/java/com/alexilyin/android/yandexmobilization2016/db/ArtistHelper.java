package com.alexilyin.android.yandexmobilization2016.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.rest.JSONDataObject;

import java.util.List;

import javax.inject.Inject;


public class ArtistHelper extends DBHelper {

    @Inject
    protected DBOpenHelper dbOpenHelper;
    @Inject
    protected ArtistGenreHelper artistGenreHelper;

    public ArtistHelper(Context context) {
        super(context);
        ((MyApplication) context).getAppComponent().inject(this);
    }

    public ContentValues getContentValues(JSONDataObject o) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.ArtistTable._ID, o.getId());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_NAME, o.getName());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_TRACKS, o.getTracks());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_ALBUMS, o.getAlbums());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_LINK, o.getLink());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_DESCRIPTION, o.getDescription());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_SMALL_COVER_URL, o.getCoverSmall());
        contentValues.put(DBContract.ArtistTable.COLUMN_NAME_BIG_COVER_URL, o.getCoverBig());
        return contentValues;
    }

//  ==============================================================================================
//  Artist table methods
//  ==============================================================================================

    public void insert(SQLiteDatabase db, @NonNull List<JSONDataObject> jsonDataObjectList) {
        // Proceed every JSONDataObject
        for (JSONDataObject o : jsonDataObjectList) {
            baseInsert(db, DBContract.ArtistTable.TABLE_NAME, getContentValues(o));
        }

    }

    // Get given artists
    public Cursor query(Long artistId) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query artist
        Cursor result = baseQuery(db,
                DBContract.ArtistTable.TABLE_NAME,
                null,
                DBContract.ArtistTable._ID + " = ?",
                artistId.toString());

        result.moveToFirst();
        return result;
    }

    // Get all artists
    public Cursor query() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query all artists
        Cursor result = baseQuery(db, DBContract.ArtistTable.TABLE_NAME);

        result.moveToFirst();
        return result;
    }


//  ==============================================================================================
//  Methods for gathering cursor data
//  ==============================================================================================


    // Get _id from cursor
    @Nullable
    public Long getId(@NonNull Cursor cursor) {
        return getLong(cursor, DBContract.ArtistTable._ID);
    }

    // Get Name from cursor
    @Nullable
    public String getName(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.ArtistTable.COLUMN_NAME_NAME);
    }

    // Get Tracks from cursor
    @Nullable
    public Integer getTracks(@NonNull Cursor cursor) {
        return getInteger(cursor, DBContract.ArtistTable.COLUMN_NAME_TRACKS);
    }

    // Get Albums from cursor
    @Nullable
    public Integer getAlbums(@NonNull Cursor cursor) {
        return getInteger(cursor, DBContract.ArtistTable.COLUMN_NAME_ALBUMS);
    }

    // Get Link from cursor
    @Nullable
    public String getLink(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.ArtistTable.COLUMN_NAME_LINK);
    }

    // Get Description from cursor
    @Nullable
    public String getDescription(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.ArtistTable.COLUMN_NAME_DESCRIPTION);
    }

    // Get urlCoverSmall from cursor
    @Nullable
    public String getSmallCover(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.ArtistTable.COLUMN_NAME_SMALL_COVER_URL);
    }

    // Get urlCoverBig from cursor
    @Nullable
    public String getBigCover(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.ArtistTable.COLUMN_NAME_BIG_COVER_URL);
    }


}
