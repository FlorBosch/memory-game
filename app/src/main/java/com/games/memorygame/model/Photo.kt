package com.games.memorygame.model


import android.net.Uri

class Photo(val title: String, val farm: Int?, val server: String, val secret: String, val id: String) {

    val url: Uri
        get() = Uri.parse(String.format(FORMAT_FLICKR_PHOTO_URL, farm.toString(),
                server, id, secret, SIZE_LARGE))

    companion object {

        private val FORMAT_FLICKR_PHOTO_URL = "http://farm%s.staticflickr.com/%s/%s_%s_%s.jpg"
        private val SIZE_LARGE = "n"
    }
}
