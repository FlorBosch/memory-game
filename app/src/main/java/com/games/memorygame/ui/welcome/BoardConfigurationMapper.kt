package com.games.memorygame.ui.welcome


import android.support.annotation.IdRes

import com.games.memorygame.R
import com.games.memorygame.model.game.LevelFactory
import com.games.memorygame.model.game.LevelType
import com.games.memorygame.model.game.PlayerMode

object BoardConfigurationMapper {

    private val levelMapper = hashMapOf(R.id.radio_easy to LevelType.EASY,
            R.id.radio_normal to LevelType.NORMAL,
            R.id.radio_hard to LevelType.HARD)

    private val playerModeMapper = hashMapOf(
            R.id.radio_single_player_mode to PlayerMode.SINGLE_PLAYER,
            R.id.radio_multi_player_mode to PlayerMode.MULTI_PLAYER)

    fun getLevelMode(@IdRes radioViewId: Int) =
            LevelFactory.getLevel(levelMapper[radioViewId] ?: LevelType.EASY)

    fun getPlayerMode(@IdRes radioViewId: Int) =
            playerModeMapper[radioViewId] ?: PlayerMode.SINGLE_PLAYER
}
