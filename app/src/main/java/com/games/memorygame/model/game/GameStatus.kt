package com.games.memorygame.model.game


import java.util.ArrayList

import javax.inject.Singleton

@Singleton
class GameStatus(val boardConfiguration: BoardConfiguration) {
    private val players = ArrayList<Player>()

    val winner: Player
        get() {
            var winner = players[0]
            players.forEach {
                if (it.score > winner.score) {
                    winner = it
                }
            }
            return winner
        }

    val numberOfCards: Int
        get() = boardConfiguration.rows * boardConfiguration.columns

    val playerTurn: Player?
        get() = players.find { it.hasTurn() }

    init {
        players.add(Player(true))
        if (this.boardConfiguration.isMultiPLayerMode) {
            players.add(Player(false))
        }
    }

    fun getPlayers(): List<Player> {
        return players
    }

    fun changeTurn() {
        if (!boardConfiguration.isMultiPLayerMode) {
            return
        }
        players.forEach { it.changeTurn() }
    }

    fun addMatchPoint() {
        playerTurn!!.addPoints(boardConfiguration.level!!.pointsPerMatch)
    }

}
