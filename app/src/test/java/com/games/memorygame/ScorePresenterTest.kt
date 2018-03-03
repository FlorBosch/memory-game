package com.games.memorygame


import com.games.memorygame.model.Score
import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.score.ScoreMvpView
import com.games.memorygame.ui.score.ScorePresenter

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import java.util.ArrayList

import io.reactivex.Flowable

import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class ScorePresenterTest {

    @Mock
    var mockScoreMvpView: ScoreMvpView? = null

    @Mock
    var mockScoreDataSource: ScoreDataSource? = null

    private var scorePresenter: ScorePresenter? = null

    @Rule
    @JvmField
    val rule = RxSchedulersRule()

    private val scores: List<Score>
        get() {
            val scores = ArrayList<Score>()
            (0 until 29).forEach { scores.add(Score("Player " + it, it)) }
            return scores
        }

    @Before
    fun setUp() {
        scorePresenter = ScorePresenter(mockScoreDataSource!!)
        scorePresenter!!.attachView(mockScoreMvpView!!)
    }

    @After
    fun tearDown() {
        scorePresenter!!.detachView()
    }

    @Test
    fun loadScores() {
        val scores = scores
        `when`(mockScoreDataSource!!.loadScores())
                .thenReturn(Flowable.just(scores))

        scorePresenter!!.loadScores()
        verify<ScoreMvpView>(mockScoreMvpView).displayScores(scores)
        verify<ScoreMvpView>(mockScoreMvpView, never()).onError()
    }

    @Test
    fun loadScoresFails() {
        `when`(mockScoreDataSource!!.loadScores())
                .thenReturn(Flowable.error(Throwable()))

        scorePresenter!!.loadScores()
        verify<ScoreMvpView>(mockScoreMvpView).onError()
        verify<ScoreMvpView>(mockScoreMvpView, never()).displayScores(ArrayList())
    }
}

