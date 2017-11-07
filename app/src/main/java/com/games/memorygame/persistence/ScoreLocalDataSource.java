package com.games.memorygame.persistence;


import com.games.memorygame.model.Score;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class ScoreLocalDataSource implements ScoreDataSource {

    private ScoreDao scoreDao;

    @Inject
    public ScoreLocalDataSource(ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
    }

    @Override
    public Flowable<List<Score>> loadScores() {
        return scoreDao.getAllScores();
    }

    @Override
    public void addScore(Score score) {
        scoreDao.insertScore(score);
    }

    @Override
    public void clearData() {
        scoreDao.deleteAllScores();
    }
}