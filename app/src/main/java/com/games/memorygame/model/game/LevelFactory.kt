package com.games.memorygame.model.game


object LevelFactory {

    fun getLevel(type: LevelType): Level {
        when (type) {
            LevelType.EASY -> return Level(3, 2, 40, 10)
            LevelType.NORMAL -> return Level(4, 3, 80, 20)
            LevelType.HARD -> return Level(5, 4, 120, 30)
            else -> return Level(3, 2, 40, 10)
        }
    }
}
