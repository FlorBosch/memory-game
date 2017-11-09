package com.games.memorygame.ui.board


import com.games.memorygame.model.Photo
import com.games.memorygame.model.game.Player
import com.games.memorygame.ui.MvpView

interface BoardMvpView : MvpView {

    fun showDashboard(photos: List<Photo>, rows: Int, columns: Int)
    fun onError(errorMessage: String)
    fun onNetworkError()
    fun updateScore(players: List<Player>)
    fun updatePlayerTurn(players: List<Player>)
    fun setUpTimer(seconds: Int)
}
