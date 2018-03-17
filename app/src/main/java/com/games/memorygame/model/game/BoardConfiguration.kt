package com.games.memorygame.model.game


import javax.inject.Singleton

@Singleton
class BoardConfiguration {

    var level: Level = LevelFactory.getLevel(LevelType.EASY)
    var playerMode: PlayerMode = PlayerMode.SINGLE_PLAYER

    val rows: Int
        get() = level.rows

    val columns: Int
        get() = level.columns

    val isMultiPLayerMode: Boolean
        get() = playerMode == PlayerMode.MULTI_PLAYER

}
