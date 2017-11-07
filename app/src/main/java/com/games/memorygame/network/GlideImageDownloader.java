package com.games.memorygame.network;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.games.memorygame.injection.ApplicationContext;
import com.games.memorygame.model.Photo;

import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class GlideImageDownloader implements ImageDownloader {

    private Context context;

    @Inject
    public GlideImageDownloader(@ApplicationContext Context context) {
        this.context = context;
    }

    // TODO Each photo could be downloaded in a different thread and not in the same
    @Override
    public void downloadPhotos(List<Photo> photos) throws Exception {
        for (Photo photo : photos) {
            Glide.with(context)
                    .load(photo.getUrl())
                    .downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get();
        }
    }
}
