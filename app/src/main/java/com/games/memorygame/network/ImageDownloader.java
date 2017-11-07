package com.games.memorygame.network;


import com.games.memorygame.model.Photo;

import java.util.List;

public interface ImageDownloader {

    void downloadPhotos(List<Photo> photos) throws Exception;
}
