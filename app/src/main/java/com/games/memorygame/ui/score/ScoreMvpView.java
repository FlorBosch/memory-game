package com.themobilecompany.memorygame.ui.score;


import com.themobilecompany.memorygame.model.Score;
import com.themobilecompany.memorygame.ui.MvpView;

import java.util.List;

public interface ScoreMvpView extends MvpView {

    void displayScores(List<Score> scores);
    void onError();
}
