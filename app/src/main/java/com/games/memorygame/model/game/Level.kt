package com.games.memorygame.model.game


data class Level(val rows: Int, val columns: Int, val seconds: Int, val pointsPerMatch: Int)

enum class LevelType {
    EASY,
    NORMAL,
    HARD
}
