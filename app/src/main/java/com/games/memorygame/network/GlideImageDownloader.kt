package com.games.memorygame.network


import android.content.Context

import com.bumptech.glide.Glide
import com.games.memorygame.injection.ApplicationContext
import com.games.memorygame.model.Photo

import javax.inject.Inject

import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

class GlideImageDownloader @Inject
constructor(@param:ApplicationContext private val context: Context) : ImageDownloader {

    // TODO Each photo could be downloaded in a different thread and not in the same
    @Throws(Exception::class)
    override fun downloadPhotos(photos: List<Photo>) {
        for (photo in photos) {
            Glide.with(context)
                    .load(photo.url)
                    .downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get()
        }
    }
}
