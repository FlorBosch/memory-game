package com.games.memorygame.model.game;


import javax.inject.Singleton;

@Singleton
public class BoardConfiguration {

    private Level level;
    private PlayerMode playerMode;

    public BoardConfiguration() {
        this.level = LevelFactory.getLevel(LevelType.EASY);
        this.playerMode = PlayerMode.SINGLE_PLAYER;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPlayerMode(PlayerMode playerMode) {
        this.playerMode = playerMode;
    }

    public int getRows() {
        return level.getRows();
    }

    public int getColumns() {
        return level.getColumns();
    }

    public boolean isMultiPLayerMode() {
        return getPlayerMode().equals(PlayerMode.MULTI_PLAYER);
    }

    public Level getLevel() {
        return level;
    }

    public PlayerMode getPlayerMode() {
        return playerMode;
    }
}
