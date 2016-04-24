package com.alexilyin.android.yandexmobilization2016;

import android.app.Application;
import android.content.Intent;

import com.alexilyin.android.yandexmobilization2016.db.DownloadService;
import com.alexilyin.android.yandexmobilization2016.di.AppComponent;
import com.alexilyin.android.yandexmobilization2016.di.AppModule;
import com.alexilyin.android.yandexmobilization2016.di.DBModule;
import com.alexilyin.android.yandexmobilization2016.di.DaggerAppComponent;
import com.alexilyin.android.yandexmobilization2016.di.RESTModule;


public class MyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dBModule(new DBModule())
                .rESTModule(new RESTModule())
                .build();

        startService(new Intent(this, DownloadService.class));

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
