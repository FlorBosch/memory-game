package com.games.memorygame.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "scores")
public class Score {

    @NonNull
    @PrimaryKey
    private String id;

    private String user;

    private Date date;

    private Integer scorePoints;

    @Ignore
    public Score(String user, Integer scorePoints) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.date = new Date();
        this.scorePoints = scorePoints;
    }

    public Score(String id, String user, Date date, Integer scorePoints) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.scorePoints = scorePoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(Integer scorePoints) {
        this.scorePoints = scorePoints;
    }
}
