package com.alexilyin.android.yandexmobilization2016.utils;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.alexilyin.android.yandexmobilization2016.db.ArtistGenreHelper;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;

import java.util.List;


public class Misc {

    // Constants
    public static final String DATA_UPDATED_ACTION = "com.alexilyin.android.yandexmobilization2016.DATA_UPDATED";

    private static final String DELIMITER_STRING = ", ";



    private ArtistHelper artistHelper;
    private ArtistGenreHelper artistGenreHelper;

    public Misc(ArtistHelper artistHelper, ArtistGenreHelper artistGenreHelper) {
        this.artistHelper = artistHelper;
        this.artistGenreHelper = artistGenreHelper;
    }

    // Build genre string
    public String buildGenreString(Cursor cursor) {
        StringBuilder result = new StringBuilder();

        Long artistId = artistHelper.getId(cursor);
        List<String> genres = null;
        if (artistId != null) {
            genres = artistGenreHelper.getGenreNameByArtist(artistId);
        }

        if (genres != null && !genres.isEmpty())
            for (int i = 0; i < genres.size(); i++) {
                result.append(genres.get(i));
                if (i < genres.size() - 1) // avoid in last iteration
                    result.append(DELIMITER_STRING);
            }

        return result.toString();
    }

    // Build tracks and albums string
    public String buildAlbumsAndTracksString(@NonNull Cursor cursor) {
        Integer albums = artistHelper.getAlbums(cursor);
        Integer tracks = artistHelper.getTracks(cursor);

        return String.valueOf(albums) + " " + declensionWordAlbum(albums) + ", " +
                tracks + " " + declensionWordTrack(tracks);
    }

    private String declensionWordAlbum(Integer number) {
        if (number > 10 && number < 15) {
            return "альбомов";
        } else {
            switch (number % 10) {
                case 1:
                    return "альбом";
                case 2:
                case 3:
                case 4:
                    return "альбомa";
                default:
                    return "альбомов";
            }
        }
    }

    private String declensionWordTrack(Integer number) {
        if (number > 10 && number < 15) {
            return "песен";
        } else {
            switch (number % 10) {
                case 1:
                    return "песня";
                case 2:
                case 3:
                case 4:
                    return "песни";
                default:
                    return "песен";
            }
        }
    }


}


/*
0 альбомов, 0 песен
1 альбом, 1 песня
2 альбома, 2 песни
3 альбома, 3 песни
4 альбома, 4 песни
5 альбомов, 5 песен
6 альбомов, 6 песен
7 альбомов, 7 песен
8 альбомов, 8 песен
9 альбомов, 9 песен
10 альбомов, 10 песен
11 альбомов, 11 песен
12 альбомов, 12 песен
13 альбомов, 13 песен
14 альбомов, 14 песен
15 альбомов, 15 песен
16 альбомов, 16 песен
17 альбомов, 17 песен
18 альбомов, 18 песен
19 альбомов, 19 песен
20 альбомов, 20 песен
21 альбом, 21 песня
22 альбома, 22 песни
23 альбома, 23 песни
24 альбома, 24 песни
25 альбомов, 25 песен
26 альбомов, 26 песен
27 альбомов, 27 песен
28 альбомов, 28 песен
29 альбомов, 29 песен
...
*/
