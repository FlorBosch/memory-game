package com.themobilecompany.memorygame.model.game;

public class Player {

    private int score;
    private boolean turn;

    public Player(boolean turn) {
        this.score = 0;
        this.turn = turn;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        this.score += points;
    }

    public boolean hasTurn() {
        return this.turn;
    }

    public void changeTurn() {
        this.turn = !this.turn;
    }

}
