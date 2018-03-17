package com.games.memorygame.ui.board

import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.model.game.GameStatus
import com.games.memorygame.model.Photo
import com.games.memorygame.model.Score
import com.games.memorygame.network.ImageDownloader
import com.games.memorygame.network.MemoryGameService
import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.BasePresenter
import com.games.memorygame.util.Constants
import com.games.memorygame.util.observeOnMainThread
import io.reactivex.functions.Action

import java.util.ArrayList
import java.util.Collections

import timber.log.Timber

class BoardPresenter(boardConfiguration: BoardConfiguration,
                     private val service: MemoryGameService,
                     private val scoreLocalDataSource: ScoreDataSource,
                     private val imageDownloader: ImageDownloader) : BasePresenter<BoardMvpView>() {

    val gameStatus: GameStatus = GameStatus(boardConfiguration)

    fun loadBoard() {
        assertViewAttached()
        service.getPhotos(gameStatus.numberOfCards / 2, Constants.PHOTO_THEME)
                .observeOnMainThread()
                .subscribe(
                        { response ->
                            if (!response.hasError()) {
                                downloadPhotos(response.getPhotos().toMutableList())
                            } else {
                                val error = response.errorMessage
                                Timber.e(error)
                                view?.onError(error)
                            }
                        },
                        { throwable ->
                            Timber.e(throwable, throwable.message)
                            view?.onNetworkError()
                        },
                        {},
                        { addDisposable(it) }
                )
    }

    fun onMatchEvent(isMatch: Boolean) {
        assertViewAttached()
        if (isMatch) {
            gameStatus.addMatchPoint()
            view?.updateScore(gameStatus.getPlayers())
            return
        }
        gameStatus.changeTurn()
        view?.updatePlayerTurn(gameStatus.getPlayers())
    }

    fun saveScore(userName: String) {
        val score = gameStatus.winner?.score ?: 0
        Action { scoreLocalDataSource.addScore(Score(userName, score)) }
                .observeOnMainThread()
                .subscribe()
    }

    private fun downloadPhotos(photos: MutableList<Photo>) {
        addDisposable(
                Action { imageDownloader.downloadPhotos(photos) }
                        .observeOnMainThread()
                        .subscribe(
                                { showBoard(photos) },
                                { throwable ->
                                    Timber.e(throwable.message)
                                    view?.onNetworkError()
                                }
                        )
        )
    }

    private fun showBoard(photos: MutableList<Photo>) {
        val configuration = gameStatus.boardConfiguration
        if (!configuration.isMultiPLayerMode) view?.setUpTimer(configuration.level.seconds)
        photos.addAll(ArrayList(photos))
        Collections.shuffle(photos)
        view?.showDashboard(photos, configuration.rows, configuration.columns)
        view?.updatePlayerTurn(gameStatus.getPlayers())
        view?.updateScore(gameStatus.getPlayers())
    }

}
