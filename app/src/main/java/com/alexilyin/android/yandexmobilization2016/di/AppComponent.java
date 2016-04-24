package com.alexilyin.android.yandexmobilization2016.di;

import com.alexilyin.android.yandexmobilization2016.db.ArtistGenreHelper;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;
import com.alexilyin.android.yandexmobilization2016.db.DBHelper;
import com.alexilyin.android.yandexmobilization2016.db.DownloadService;
import com.alexilyin.android.yandexmobilization2016.db.GenreHelper;
import com.alexilyin.android.yandexmobilization2016.view.ArtistRecyclerViewAdapter;
import com.alexilyin.android.yandexmobilization2016.view.DetailActivity;
import com.alexilyin.android.yandexmobilization2016.view.MainActivity;
import com.alexilyin.android.yandexmobilization2016.MyApplication;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, DBModule.class, RESTModule.class})
public interface AppComponent {
    void inject(MyApplication obj);

    void inject(MainActivity obj);

    void inject(ArtistRecyclerViewAdapter obj);

    void inject(ArtistHelper obj);

    void inject(GenreHelper obj);

    void inject(ArtistGenreHelper obj);

    void inject(DetailActivity obj);

    void inject(DownloadService service);
}
