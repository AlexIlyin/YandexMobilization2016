package com.alexilyin.android.yandexmobilization2016.di;

import android.content.Context;

import com.alexilyin.android.yandexmobilization2016.utils.Misc;
import com.alexilyin.android.yandexmobilization2016.db.ArtistGenreHelper;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    Misc providesMisc(ArtistHelper artistHelper, ArtistGenreHelper artistGenreHelper) {
        return new Misc(artistHelper, artistGenreHelper);
    }
}
