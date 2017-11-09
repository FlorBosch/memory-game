package com.games.memorygame

import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.model.game.GameStatus
import com.games.memorygame.model.game.LevelFactory
import com.games.memorygame.model.game.LevelType
import com.games.memorygame.model.game.Player
import com.games.memorygame.model.game.PlayerMode

import org.junit.Test

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo


class GameStatusTest {

    private val boardConfiguration: BoardConfiguration
        get() {
            val configuration = BoardConfiguration()
            configuration.level = LevelFactory.getLevel(LevelType.EASY)
            configuration.playerMode = PlayerMode.MULTI_PLAYER
            return configuration
        }

    @Test
    fun testBoardConfiguration() {
        val configuration = boardConfiguration
        var gameStatus = GameStatus(configuration)
        val numberOfCards = configuration.columns * configuration.rows

        assertThat(gameStatus.numberOfCards, `is`(numberOfCards))
        assertThat(gameStatus.getPlayers().size, `is`(2))

        configuration.playerMode = PlayerMode.SINGLE_PLAYER
        gameStatus = GameStatus(configuration)
        assertThat(gameStatus.getPlayers().size, `is`(1))
    }

    @Test
    fun testChangeTurn() {
        val gameStatus = GameStatus(boardConfiguration)
        val players = gameStatus.getPlayers()
        assertThat<Player>(gameStatus.playerTurn, equalTo(players[0]))
        gameStatus.changeTurn()
        assertThat<Player>(gameStatus.playerTurn, equalTo(players[1]))
    }

    @Test
    fun testAddScore() {
        val gameStatus = GameStatus(boardConfiguration)

        assertThat(getScore(gameStatus, 0), `is`(0))
        assertThat(getScore(gameStatus, 1), `is`(0))

        gameStatus.addMatchPoint()
        assertThat(getScore(gameStatus, 0), `is`(10))
        assertThat(getScore(gameStatus, 1), `is`(0))
        gameStatus.changeTurn()
        gameStatus.addMatchPoint()
        assertThat(getScore(gameStatus, 0), `is`(10))
        assertThat(getScore(gameStatus, 1), `is`(10))

        assertThat(gameStatus.winner, equalTo(gameStatus.getPlayers()[0]))
    }

    private fun getScore(gameStatus: GameStatus, playerIndex: Int): Int {
        return gameStatus.getPlayers()[playerIndex].score
    }
}
