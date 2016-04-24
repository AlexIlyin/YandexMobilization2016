package com.alexilyin.android.yandexmobilization2016.di;

import android.content.Context;

import com.alexilyin.android.yandexmobilization2016.rest.YandexService;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RESTModule {


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(YandexService.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(YandexService.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(YandexService.TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(), 100000000))
                .build();
    }

    @Singleton
    @Provides
    GsonBuilder provideGSON() {
        return new GsonBuilder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(GsonBuilder gsonBuilder) {
        return new Retrofit.Builder()
                .baseUrl(YandexService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();
    }

    @Singleton
    @Provides
    YandexService provideYandexService(Retrofit retrofit) {
        return retrofit.create(YandexService.class);
    }

    @Singleton
    @Provides
    Picasso providePicasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }


}
