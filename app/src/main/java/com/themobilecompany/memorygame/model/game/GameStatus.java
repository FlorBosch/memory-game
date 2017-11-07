package com.themobilecompany.memorygame.model.game;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class GameStatus {

    private BoardConfiguration boardConfiguration;
    private List<Player> players = new ArrayList<>();

    public GameStatus(BoardConfiguration boardConfiguration) {
        this.boardConfiguration = boardConfiguration;
        players.add(new Player(true));
        if (this.boardConfiguration.isMultiPLayerMode()) {
            players.add(new Player(false));
        }
    }

    public BoardConfiguration getBoardConfiguration() {
        return boardConfiguration;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    public int getNumberOfCards() {
        return getBoardConfiguration().getRows() * getBoardConfiguration().getColumns();
    }

    public Player getPlayerTurn() {
        for (Player player : players) {
            if (player.hasTurn()) {
                return player;
            }
        }
        return null;
    }

    public void changeTurn() {
        if (!boardConfiguration.isMultiPLayerMode()) {
            return;
        }
        for (Player player : players) {
            player.changeTurn();
        }
    }

    public void addMatchPoint() {
        getPlayerTurn().addPoints(boardConfiguration.getLevel().getPointsPerMatch());
    }

}
