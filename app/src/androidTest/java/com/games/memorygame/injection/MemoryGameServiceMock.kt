package com.games.memorygame.injection

import com.games.memorygame.model.Photo
import com.games.memorygame.model.Photos
import com.games.memorygame.network.FlickrResponse
import com.games.memorygame.network.MemoryGameService

import java.util.ArrayList

import io.reactivex.Observable
import retrofit2.http.Query

class MemoryGameServiceMock : MemoryGameService {

    override fun getPhotos(@Query("per_page") perPage: Int,
                           @Query("text") theme: String): Observable<FlickrResponse> {
        val photos = ArrayList<Photo>()
        (0 until perPage).forEach { photos.add(Photo("Title " + it, it, "", "", it.toString())) }
        return Observable.just(FlickrResponse(Photos(photos), "ok", null, ""))
    }

}
