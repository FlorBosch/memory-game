package com.games.memorygame.model.game

class Player(private var turn: Boolean) {

    var score: Int = 0
        private set

    init {
        this.score = 0
    }

    fun addPoints(points: Int) {
        this.score += points
    }

    fun hasTurn(): Boolean {
        return this.turn
    }

    fun changeTurn() {
        this.turn = !this.turn
    }

}
