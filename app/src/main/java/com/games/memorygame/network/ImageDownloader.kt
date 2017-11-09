package com.games.memorygame.network


import com.games.memorygame.model.Photo

interface ImageDownloader {

    @Throws(Exception::class)
    fun downloadPhotos(photos: List<Photo>)
}
