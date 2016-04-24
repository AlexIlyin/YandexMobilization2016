package com.alexilyin.android.yandexmobilization2016.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexilyin.android.yandexmobilization2016.MyApplication;
import com.alexilyin.android.yandexmobilization2016.R;
import com.alexilyin.android.yandexmobilization2016.utils.Misc;
import com.alexilyin.android.yandexmobilization2016.db.ArtistGenreHelper;
import com.alexilyin.android.yandexmobilization2016.db.ArtistHelper;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ArtistRecyclerViewAdapter extends CursorRecyclerViewAdapter<ArtistRecyclerViewAdapter.Holder> {

    @Inject
    protected ArtistHelper artistHelper;
    @Inject
    protected ArtistGenreHelper artistGenreHelper;
    @Inject
    protected Misc misc;
    @Inject
    protected Picasso picasso;

    private Activity activity;

    public ArtistRecyclerViewAdapter(Activity activity, Cursor cursor) {
        super(activity, cursor);
        ((MyApplication) activity.getApplicationContext()).getAppComponent().inject(this);
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(final Holder viewHolder, Cursor cursor) {

        viewHolder.id = artistHelper.getId(cursor);
        viewHolder.name.setText(artistHelper.getName(cursor));
        viewHolder.genres.setText(misc.buildGenreString(cursor));
        viewHolder.albumsAndTracks.setText(misc.buildAlbumsAndTracksString(cursor));

        // Loading cover image
        String url = artistHelper.getSmallCover(cursor);
        picasso.with(activity).load(url).fit().centerCrop().placeholder(R.drawable.placeholder).into(viewHolder.cover);

        // click on item
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, viewHolder.cover, "cover_transition");
                Intent intent = DetailActivity.prepareIntent(activity, viewHolder.id);
                activity.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.artist_recyclerview_item,
                parent,
                false
        );

        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {

        Long id;
        TextView name;
        TextView genres;
        TextView albumsAndTracks;
        ImageView cover;

        public Holder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.artist_recyclerview_name);
            genres = (TextView) itemView.findViewById(R.id.artist_recyclerview_genres);
            albumsAndTracks = (TextView) itemView.findViewById(R.id.artist_recyclerview_albums_and_tracks);
            cover = (ImageView) itemView.findViewById(R.id.artist_recyclerview_cover);

        }

    }
}

