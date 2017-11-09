package com.games.memorygame.ui.score


import com.games.memorygame.model.Score
import com.games.memorygame.ui.MvpView

interface ScoreMvpView : MvpView {

    fun displayScores(scores: List<Score>)
    fun onError()
}
