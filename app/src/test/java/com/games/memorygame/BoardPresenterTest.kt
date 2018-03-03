package com.games.memorygame


import com.games.memorygame.model.Photo
import com.games.memorygame.model.Photos
import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.model.game.Player
import com.games.memorygame.model.game.PlayerMode
import com.games.memorygame.network.FlickrResponse
import com.games.memorygame.network.ImageDownloader
import com.games.memorygame.network.MemoryGameService
import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.board.BoardMvpView
import com.games.memorygame.ui.board.BoardPresenter
import com.games.memorygame.util.Constants

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner

import java.util.ArrayList

import io.reactivex.Observable

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class BoardPresenterTest {

    @Mock
    var mockBoardMvpView: BoardMvpView? = null

    @Mock
    var mockMemoryGameService: MemoryGameService? = null

    @Mock
    var mockScoreDataSource: ScoreDataSource? = null

    @Mock
    var mockImageDownloader: ImageDownloader? = null

    private var boardConfiguration: BoardConfiguration? = null
    private var boardPresenter: BoardPresenter? = null

    @Rule
    @JvmField
    val rule = RxSchedulersRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        boardConfiguration = BoardConfiguration()
        boardConfiguration!!.playerMode = PlayerMode.MULTI_PLAYER
        boardPresenter = BoardPresenter(boardConfiguration!!, mockMemoryGameService!!,
                mockScoreDataSource!!, mockImageDownloader!!)
        boardPresenter!!.attachView(mockBoardMvpView!!)
    }

    @After
    fun tearDown() {
        boardPresenter!!.detachView()
    }

    @Test
    fun loadBoard() {
        val gameStatus = boardPresenter!!.gameStatus
        val numberOfPhotos = gameStatus.numberOfCards / 2
        val response = getFlickrResponse(numberOfPhotos)
        `when`(mockMemoryGameService!!.getPhotos(numberOfPhotos, Constants.PHOTO_THEME))
                .thenReturn(Observable.just(response))

        boardPresenter!!.loadBoard()
        verify<BoardMvpView>(mockBoardMvpView).showDashboard(response.getPhotos(),
                boardConfiguration!!.rows, boardConfiguration!!.columns)
        verify<BoardMvpView>(mockBoardMvpView).updateScore(gameStatus.getPlayers())
        verify<BoardMvpView>(mockBoardMvpView).updatePlayerTurn(gameStatus.getPlayers())
        verify<BoardMvpView>(mockBoardMvpView, never()).onNetworkError()
    }

    @Test
    fun loadBoardFails() {
        val gameStatus = boardPresenter!!.gameStatus
        `when`(mockMemoryGameService!!.getPhotos(gameStatus.numberOfCards / 2, Constants.PHOTO_THEME))
                .thenReturn(Observable.error(Throwable()))

        boardPresenter!!.loadBoard()
        verify<BoardMvpView>(mockBoardMvpView).onNetworkError()
        verify<BoardMvpView>(mockBoardMvpView, never()).showDashboard(ArrayList(),
                boardConfiguration!!.rows, boardConfiguration!!.columns)
    }

    @Test
    fun onMatchEvent() {
        val gameStatus = boardPresenter!!.gameStatus
        assertThat<Player>(gameStatus.playerTurn, equalTo<Player>(gameStatus.getPlayers()[0]))
        boardPresenter!!.onMatchEvent(true)
        assertThat<Player>(gameStatus.playerTurn, equalTo<Player>(gameStatus.getPlayers()[0]))
        assertThat(gameStatus.getPlayers()[0].score, `is`(10))
        assertThat(gameStatus.getPlayers()[1].score, `is`(0))
        boardPresenter!!.onMatchEvent(false)
        assertThat<Player>(gameStatus.playerTurn, equalTo<Player>(gameStatus.getPlayers()[1]))
    }

    private fun getFlickrResponse(numberOfPhotos: Int): FlickrResponse {
        val photos = ArrayList<Photo>()
        (0 until numberOfPhotos).forEach {
            photos.add(Photo("Title " + it, it, "", "", it.toString()))
        }
        return FlickrResponse(Photos(photos), "ok", null, "")
    }

}

