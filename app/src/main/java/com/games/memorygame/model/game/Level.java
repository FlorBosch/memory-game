package com.games.memorygame.model.game;


public class Level {

    private int rows;
    private int columns;
    private int seconds;
    private int pointsPerMatch;

    public Level(int rows, int columns, int seconds, int pointsPerMatch) {
        this.rows = rows;
        this.columns = columns;
        this.seconds = seconds;
        this.pointsPerMatch = pointsPerMatch;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getPointsPerMatch() {
        return pointsPerMatch;
    }
}
