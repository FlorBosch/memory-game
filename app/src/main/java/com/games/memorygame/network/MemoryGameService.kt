package com.games.memorygame.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface MemoryGameService {

    @GET("/services/rest/?method=flickr.photos.search&page=1")
    fun getPhotos(@Query("per_page") perPage: Int,
                  @Query("text") theme: String): Observable<FlickrResponse>

}
