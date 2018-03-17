package com.games.memorygame.persistence


import com.games.memorygame.model.Score

import javax.inject.Inject

import io.reactivex.Flowable

class ScoreLocalDataSource @Inject
constructor(private val scoreDao: ScoreDao) : ScoreDataSource {

    override fun loadScores(): Flowable<List<Score>> = scoreDao.allScores

    override fun addScore(score: Score) {
        scoreDao.insertScore(score)
    }

    override fun clearData() {
        scoreDao.deleteAllScores()
    }
}