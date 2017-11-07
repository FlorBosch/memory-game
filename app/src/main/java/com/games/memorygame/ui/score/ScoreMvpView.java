package com.games.memorygame.ui.score;


import com.games.memorygame.model.Score;
import com.games.memorygame.ui.MvpView;

import java.util.List;

public interface ScoreMvpView extends MvpView {

    void displayScores(List<Score> scores);
    void onError();
}
