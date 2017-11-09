package com.games.memorygame.model.game


import javax.inject.Singleton

@Singleton
class BoardConfiguration {

    var level: Level? = null
    var playerMode: PlayerMode? = null

    val rows: Int
        get() = level!!.rows

    val columns: Int
        get() = level!!.columns

    val isMultiPLayerMode: Boolean
        get() = playerMode == PlayerMode.MULTI_PLAYER

    init {
        this.level = LevelFactory.getLevel(LevelType.EASY)
        this.playerMode = PlayerMode.SINGLE_PLAYER
    }
}
