package com.games.memorygame.persistence;


import com.games.memorygame.model.Score;

import java.util.List;

import io.reactivex.Flowable;

public interface ScoreDataSource {

    Flowable<List<Score>> loadScores();

    void addScore(Score score);

    void clearData();
}