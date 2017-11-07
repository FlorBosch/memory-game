package com.games.memorygame.network;


import com.games.memorygame.model.Photo;
import com.games.memorygame.model.Photos;

import java.util.List;

public class FlickrResponse {

    private final Photos photos;
    private final String stat;
    private final Integer code;
    private final String message;

    public FlickrResponse(Photos photos, String stat, Integer code, String message) {
        this.photos = photos;
        this.stat = stat;
        this.code = code;
        this.message = message;
    }

    public List<Photo> getPhotos() {
        return photos.getPhoto();
    }

    public boolean hasError() {
        return !stat.equals("ok");
    }

    public String getErrorMessage() {
        return message;
    }
}
