package com.games.memorygame.model.game

class Player(private var turn: Boolean) {

    var score: Int = 0
        private set

    fun addPoints(points: Int) {
        this.score += points
    }

    fun hasTurn(): Boolean = this.turn

    fun changeTurn() {
        this.turn = !this.turn
    }

}

enum class PlayerMode {
    SINGLE_PLAYER,
    MULTI_PLAYER
}
