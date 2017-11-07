package com.themobilecompany.memorygame.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MemoryGameService {

    @GET("/services/rest/?method=flickr.photos.search&page=1")
    Observable<FlickrResponse> getPhotos(@Query("per_page") int perPage,
                                         @Query("text") String theme);

}
