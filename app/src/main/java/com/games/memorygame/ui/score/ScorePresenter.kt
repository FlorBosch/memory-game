package com.games.memorygame.ui.score

import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.BasePresenter

import javax.inject.Inject

import timber.log.Timber

import com.games.memorygame.util.RxUtil.call


class ScorePresenter @Inject
constructor(private val scoreLocalDataSource: ScoreDataSource) : BasePresenter<ScoreMvpView>() {

    fun loadScores() {
        assertViewAttached()
        addDisposable(call(scoreLocalDataSource.loadScores()).subscribe(
                { scores -> view!!.displayScores(scores) }
        ) { throwable ->
            Timber.e(throwable.message)
            view!!.onError()
        }
        )
    }

}
