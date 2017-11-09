package com.games.memorygame.ui.welcome


import android.support.annotation.IdRes

import com.games.memorygame.R
import com.games.memorygame.model.game.Level
import com.games.memorygame.model.game.LevelFactory
import com.games.memorygame.model.game.LevelType
import com.games.memorygame.model.game.PlayerMode

import java.util.HashMap

object BoardConfigurationMapper {

    private val levelMapper = object : HashMap<Int, LevelType>() {
        init {
            put(R.id.radio_easy, LevelType.EASY)
            put(R.id.radio_normal, LevelType.NORMAL)
            put(R.id.radio_hard, LevelType.HARD)
        }
    }

    private val playerModeMapper = object : HashMap<Int, PlayerMode>() {
        init {
            put(R.id.radio_single_player_mode, PlayerMode.SINGLE_PLAYER)
            put(R.id.radio_multi_player_mode, PlayerMode.MULTI_PLAYER)
        }
    }

    fun getLevelMode(@IdRes radioViewId: Int): Level {
        return LevelFactory.getLevel(levelMapper[radioViewId]!!)
    }

    fun getPlayerMode(@IdRes radioViewId: Int): PlayerMode {
        return playerModeMapper[radioViewId]!!
    }
}
