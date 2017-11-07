package com.games.memorygame.injection;

import com.games.memorygame.model.Score;
import com.games.memorygame.persistence.ScoreDataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.Flowable;

public class ScoreDataSourceMock implements ScoreDataSource {

    private List<Score> scores = new ArrayList<>();

    @Override
    public Flowable<List<Score>> loadScores() {
        Date date = new GregorianCalendar(2017, 7, 27).getTime();
        for (int i = 0; i < 11; i++) {
            scores.add(new Score(String.valueOf(i), "User " + i, date, i));
        }
        return Flowable.just(scores);
    }

    @Override
    public void addScore(Score score) {
        scores.add(score);
    }

    @Override
    public void clearData() {
        scores.clear();
    }

}
