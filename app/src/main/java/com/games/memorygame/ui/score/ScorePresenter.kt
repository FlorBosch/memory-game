package com.games.memorygame.ui.score

import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.BasePresenter

import timber.log.Timber

import com.games.memorygame.util.observeOnMainThread


class ScorePresenter(private val scoreLocalDataSource: ScoreDataSource) :
        BasePresenter<ScoreMvpView>() {

    fun loadScores() {
        assertViewAttached()
        addDisposable(
                scoreLocalDataSource.loadScores()
                        .observeOnMainThread()
                        .subscribe(
                                { scores -> view!!.displayScores(scores) },
                                { throwable ->
                                    Timber.e(throwable.message)
                                    view?.onError()
                                }
                        )
        )
    }

}
