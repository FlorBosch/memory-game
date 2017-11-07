package com.games.memorygame.model;


import android.net.Uri;

public class Photo {

    private static final String FORMAT_FLICKR_PHOTO_URL =
            "http://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
    private static final String SIZE_LARGE = "n";

    private final String id;
    private final String secret;
    private final String server;
    private final Integer farm;
    private final String title;

    public Photo(String title, Integer farm, String server, String secret, String id) {
        this.title = title;
        this.farm = farm;
        this.server = server;
        this.secret = secret;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public Integer getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public Uri getUrl() {
        return Uri.parse(String.format(FORMAT_FLICKR_PHOTO_URL, String.valueOf(getFarm()),
                getServer(), getId(), getSecret(), SIZE_LARGE));
    }
}
