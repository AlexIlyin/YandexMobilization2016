package com.alexilyin.android.yandexmobilization2016.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.ArtistTable.SQL_QUERY_CREATE_TABLE);
        db.execSQL(DBContract.GenreTable.SQL_QUERY_CREATE_TABLE);
        db.execSQL(DBContract.ArtistGenreTable.SQL_QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.ArtistTable.SQL_QUERY_DROP_TABLE);
        db.execSQL(DBContract.GenreTable.SQL_QUERY_DROP_TABLE);
        db.execSQL(DBContract.ArtistGenreTable.SQL_QUERY_DROP_TABLE);

        onCreate(db);
    }

}
