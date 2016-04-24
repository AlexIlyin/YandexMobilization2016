package com.alexilyin.android.yandexmobilization2016.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.rest.JSONDataObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class ArtistGenreHelper extends DBHelper {

    @Inject
    protected DBOpenHelper dbOpenHelper;
    @Inject
    protected GenreHelper genreHelper;

    public ArtistGenreHelper(Context context) {
        super(context);
        ((MyApplication) context).getAppComponent().inject(this);
    }

    public static ContentValues getContentValues(long artistId, long genreId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.ArtistGenreTable.COLUMN_NAME_ARTIST_ID, artistId);
        contentValues.put(DBContract.ArtistGenreTable.COLUMN_NAME_GENRE_ID, genreId);
        return contentValues;
    }
//  ==============================================================================================
//  ArtistGenre table methods
//  ==============================================================================================

    public void insert(SQLiteDatabase db, @NonNull List<JSONDataObject> jsonDataObjectList) {
        // Proceed every JSONDataObject
        for (JSONDataObject o : jsonDataObjectList) {

            // Proceed every genre from JSONDataObject
            long artistId = o.getId();
            for (String genreName : o.getGenres()) {

                // Get genre id
                Cursor cursor = genreHelper.query(genreName);
                Long genreId = genreHelper.getId(cursor);

                baseInsert(db, DBContract.ArtistGenreTable.TABLE_NAME, getContentValues(artistId, genreId));
            }

        }

    }

    public Cursor query() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query all artists genres
        Cursor result = baseQuery(db,
                DBContract.ArtistGenreTable.TABLE_NAME
        );
        result.moveToFirst();
        return result;
    }

    // Get genre name id list by artist id
    public List<String> getGenreNameByArtist(@NonNull Long artistId) {
        List<String> result = new ArrayList<>();
        List<Long> genreIdList = getGenreIdByArtist(artistId);

        for (Long id : genreIdList) {
            Cursor cursor = genreHelper.query(id);
            cursor.moveToFirst();
            String genre = genreHelper.getName(cursor);
            result.add(genre);
        }

        return result;
    }

    // Get genre id list by artist id
    public List<Long> getGenreIdByArtist(@NonNull Long artistId) {
        List<Long> result = new ArrayList<>();
        Cursor cursor = queryGenreByArtist(artistId);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Long genreId = getGenreId(cursor);
            result.add(genreId);
            cursor.moveToNext();
        }

        return result;
    }

    // Get genres corresponding to specific artist
    public Cursor queryGenreByArtist(Long artistId) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query all artists genres
        Cursor result = baseQuery(db,
                DBContract.ArtistGenreTable.TABLE_NAME,
                new String[]{DBContract.ArtistGenreTable.COLUMN_NAME_ARTIST_ID, DBContract.ArtistGenreTable.COLUMN_NAME_GENRE_ID},
                DBContract.ArtistGenreTable.COLUMN_NAME_ARTIST_ID + " = ?",
                artistId.toString()
        );

        result.moveToFirst();
        return result;
    }


//  ==============================================================================================
//  Methods for gathering cursor data
//  ==============================================================================================


    // Get Artist id from cursor
    @Nullable
    public Long getArtistId(@NonNull Cursor cursor) {
        return getLong(cursor, DBContract.ArtistGenreTable.COLUMN_NAME_ARTIST_ID);
    }

    // Get Genre id from cursor
    @Nullable
    public Long getGenreId(@NonNull Cursor cursor) {
        return getLong(cursor, DBContract.ArtistGenreTable.COLUMN_NAME_GENRE_ID);
    }

}
