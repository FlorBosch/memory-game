package com.themobilecompany.memorygame.persistence;


import com.themobilecompany.memorygame.model.Score;

import java.util.List;

import io.reactivex.Flowable;

public interface ScoreDataSource {

    Flowable<List<Score>> loadScores();

    void addScore(Score score);

    void clearData();
}