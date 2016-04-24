package com.alexilyin.android.yandexmobilization2016.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface YandexService {

    String URL_BASE = "http://download.cdn.yandex.net/";
    String URL_PATH = "mobilization-2016/artists.json";

    int CONNECT_TIMEOUT = 15;
    int WRITE_TIMEOUT = 60;
    int TIMEOUT = 60;

    @GET(URL_PATH)
    Call<List<JSONDataObject>> getArtistsData();
}
