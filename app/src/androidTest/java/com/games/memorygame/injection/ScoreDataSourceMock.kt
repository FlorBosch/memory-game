package com.games.memorygame.injection

import com.games.memorygame.model.Score
import com.games.memorygame.persistence.ScoreDataSource

import java.util.ArrayList
import java.util.GregorianCalendar

import io.reactivex.Flowable

class ScoreDataSourceMock : ScoreDataSource {

    private val scores = ArrayList<Score>()

    override fun loadScores(): Flowable<List<Score>> {
        val date = GregorianCalendar(2017, 7, 27).time
        (0 until 10).forEach { scores.add(Score("User " + it, it, it.toString(), date)) }
        return Flowable.just(scores)
    }

    override fun addScore(score: Score) {
        scores.add(score)
    }

    override fun clearData() {
        scores.clear()
    }

}
