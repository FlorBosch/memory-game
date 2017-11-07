package com.themobilecompany.memorygame;

import com.themobilecompany.memorygame.model.game.BoardConfiguration;
import com.themobilecompany.memorygame.model.game.GameStatus;
import com.themobilecompany.memorygame.model.game.LevelFactory;
import com.themobilecompany.memorygame.model.game.LevelType;
import com.themobilecompany.memorygame.model.game.Player;
import com.themobilecompany.memorygame.model.game.PlayerMode;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class GameStatusTest {

    @Test
    public void testBoardConfiguration() {
        BoardConfiguration configuration = getBoardConfiguration();
        GameStatus gameStatus = new GameStatus(configuration);
        int numberOfCards = configuration.getColumns() * configuration.getRows();

        assertThat(gameStatus.getNumberOfCards(), is(numberOfCards));
        assertThat(gameStatus.getPlayers().size(), is(2));

        configuration.setPlayerMode(PlayerMode.SINGLE_PLAYER);
        gameStatus = new GameStatus(configuration);
        assertThat(gameStatus.getPlayers().size(), is(1));
    }

    @Test
    public void testChangeTurn() {
        GameStatus gameStatus = new GameStatus(getBoardConfiguration());
        List<Player> players = gameStatus.getPlayers();
        assertThat(gameStatus.getPlayerTurn(), equalTo(players.get(0)));
        gameStatus.changeTurn();
        assertThat(gameStatus.getPlayerTurn(), equalTo(players.get(1)));
    }

    @Test
    public void testAddScore() {
        GameStatus gameStatus = new GameStatus(getBoardConfiguration());

        assertThat(getScore(gameStatus, 0), is(0));
        assertThat(getScore(gameStatus, 1), is(0));

        gameStatus.addMatchPoint();
        assertThat(getScore(gameStatus, 0), is(10));
        assertThat(getScore(gameStatus, 1), is(0));
        gameStatus.changeTurn();
        gameStatus.addMatchPoint();
        assertThat(getScore(gameStatus, 0), is(10));
        assertThat(getScore(gameStatus, 1), is(10));

        assertThat(gameStatus.getWinner(), equalTo(gameStatus.getPlayers().get(0)));
    }

    private BoardConfiguration getBoardConfiguration() {
        BoardConfiguration configuration = new BoardConfiguration();
        configuration.setLevel(LevelFactory.getLevel(LevelType.EASY));
        configuration.setPlayerMode(PlayerMode.MULTI_PLAYER);
        return configuration;
    }

    private int getScore(GameStatus gameStatus, int playerIndex) {
        return gameStatus.getPlayers().get(playerIndex).getScore();
    }
}
