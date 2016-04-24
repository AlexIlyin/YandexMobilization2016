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

public class GenreHelper extends DBHelper {

    @Inject
    protected DBOpenHelper dbOpenHelper;

    public GenreHelper(Context context) {
        super(context);
        ((MyApplication) context).getAppComponent().inject(this);
    }

    public static ContentValues getContentValues(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.GenreTable.COLUMN_NAME_NAME, name);
        return contentValues;
    }

//  ==============================================================================================
//  Genre table methods
//  ==============================================================================================

    // Insert data from JSONDataObject oblects
    public void insert(SQLiteDatabase db, @NonNull List<JSONDataObject> jsonDataObjectList) {

        // Proceed every JSONDataObject
        for (JSONDataObject o : jsonDataObjectList) {

            // Proceed every Genre name
            for (String genreName : o.getGenres()) {
                baseInsert(db, DBContract.GenreTable.TABLE_NAME, getContentValues(genreName));
            }
        }
    }

    // Get particular genre by genre name
    public Cursor query(String genreName) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query genre by name
        Cursor result = baseQuery(db,
                DBContract.GenreTable.TABLE_NAME,
                null,
                DBContract.GenreTable.COLUMN_NAME_NAME + " = ?",
                genreName
        );

        result.moveToFirst();
        return result;
    }


    // Get particular genre by genre id
    public Cursor query(Long genreId) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query genre by id
        Cursor result = baseQuery(db,
                DBContract.GenreTable.TABLE_NAME,
                null,
                DBContract.GenreTable._ID + " = ?",
                genreId.toString()
        );

        result.moveToFirst();
        return result;
    }

    // Get all genres
    public Cursor query() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // Query all genres
        Cursor result = baseQuery(db, DBContract.GenreTable.TABLE_NAME);
        result.moveToFirst();
        return result;
    }


//  ==============================================================================================
//  Methods for gathering cursor data
//  ==============================================================================================


    // Get _id from cursor
    @Nullable
    public Long getId(@NonNull Cursor cursor) {
        return getLong(cursor, DBContract.GenreTable._ID);
    }

    // Get Name from cursor
    @Nullable
    public String getName(@NonNull Cursor cursor) {
        return getString(cursor, DBContract.GenreTable.COLUMN_NAME_NAME);
    }


}
