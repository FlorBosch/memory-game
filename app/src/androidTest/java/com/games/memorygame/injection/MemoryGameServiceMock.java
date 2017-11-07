package com.games.memorygame.injection;

import com.games.memorygame.model.Photo;
import com.games.memorygame.model.Photos;
import com.games.memorygame.network.FlickrResponse;
import com.games.memorygame.network.MemoryGameService;

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
