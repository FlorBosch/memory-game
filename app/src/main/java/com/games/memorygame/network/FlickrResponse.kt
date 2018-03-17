package com.games.memorygame.network


import com.games.memorygame.model.Photo
import com.games.memorygame.model.Photos

class FlickrResponse(private val photos: Photos,
                     private val stat: String,
                     private val code: Int?,
                     val errorMessage: String) {

    fun getPhotos(): List<Photo> = photos.photo

    fun hasError(): Boolean = stat != "ok"
}
