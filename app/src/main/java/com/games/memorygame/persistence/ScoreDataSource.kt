package com.games.memorygame.persistence


import com.games.memorygame.model.Score

import io.reactivex.Flowable

interface ScoreDataSource {

    fun loadScores(): Flowable<List<Score>>

    fun addScore(score: Score)

    fun clearData()
}