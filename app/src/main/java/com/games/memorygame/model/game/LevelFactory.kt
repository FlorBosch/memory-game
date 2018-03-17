package com.games.memorygame.model.game


object LevelFactory {

    fun getLevel(type: LevelType): Level {
        return when (type) {
            LevelType.EASY -> Level(3, 2, 40, 10)
            LevelType.NORMAL -> Level(4, 3, 80, 20)
            LevelType.HARD -> Level(5, 4, 120, 30)
        }
    }
}
