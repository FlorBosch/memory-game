package com.themobilecompany.memorygame.injection;

import com.themobilecompany.memorygame.model.Photo;
import com.themobilecompany.memorygame.model.Photos;
import com.themobilecompany.memorygame.network.FlickrResponse;
import com.themobilecompany.memorygame.network.MemoryGameService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Query;

public class MemoryGameServiceMock implements MemoryGameService {

    @Override
    public Observable<FlickrResponse> getPhotos(@Query("per_page") int perPage,
                                                @Query("text") String theme) {
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < perPage; i++) {
            photos.add(new Photo("Title " + i, i, "", "", String.valueOf(i)));
        }
        return Observable.just(new FlickrResponse(new Photos(photos), "ok", null, ""));
    }

}
