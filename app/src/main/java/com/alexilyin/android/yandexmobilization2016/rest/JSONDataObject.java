package com.alexilyin.android.yandexmobilization2016.rest;

import java.util.List;

public class JSONDataObject {

    private long id;
    private String name;
    private List<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    private static class Cover {
        String small;
        String big;
    }

    // For log purpose
    @Override
    public String toString() {
        return new StringBuilder()
                .append("Artist no ").append(id)
                .append(", Name: ").append(name)
                .append(", Genres: ").append(genres)
                .append(", Tracks: ").append(tracks)
                .append(", Albums: ").append(albums)
                .append(", Link: ").append(link)
                .append(", Description: ").append(description)
                .append(", Small cover: ").append(cover.small)
                .append(", Big cover: ").append(cover.big)
                .toString();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverBig() {
        return cover.big;
    }

    public String getCoverSmall() {
        return cover.small;
    }
}
