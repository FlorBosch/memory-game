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
        photos.forEach {
            Glide.with(context)
                    .load(it.url)
                    .downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get()
        }
    }
}
