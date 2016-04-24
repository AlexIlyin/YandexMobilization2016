package com.alexilyin.android.yandexmobilization2016.di;

import android.content.Context;

import com.alexilyin.android.yandexmobilization2016.db.ArtistGenreHelper;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;
import com.alexilyin.android.yandexmobilization2016.db.DBHelper;
import com.alexilyin.android.yandexmobilization2016.db.DBOpenHelper;
import com.alexilyin.android.yandexmobilization2016.db.GenreHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Singleton
    @Provides
    DBOpenHelper provideDBOpenHelper(Context context) {
        return new DBOpenHelper(context);
    }

    @Singleton
    @Provides
    DBHelper provideDBHelper(Context context) {
        return new DBHelper(context);
    }

    @Singleton
    @Provides
    ArtistHelper provideArtistHelper(Context context) {
        return new ArtistHelper(context);
    }

    @Singleton
    @Provides
    ArtistGenreHelper provideArtistGenreHelper(Context context) {
        return new ArtistGenreHelper(context);
    }

    @Singleton
    @Provides
    GenreHelper provideGenreHelper(Context context) {
        return new GenreHelper(context);
    }

}
