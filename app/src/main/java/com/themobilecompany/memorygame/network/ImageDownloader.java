package com.themobilecompany.memorygame.network;


import com.themobilecompany.memorygame.model.Photo;

import java.util.List;

public interface ImageDownloader {

    void downloadPhotos(List<Photo> photos) throws Exception;
}
