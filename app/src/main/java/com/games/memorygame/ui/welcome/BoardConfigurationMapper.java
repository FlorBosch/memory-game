package com.themobilecompany.memorygame.ui.welcome;


import android.support.annotation.IdRes;

import com.themobilecompany.memorygame.R;
import com.themobilecompany.memorygame.model.game.Level;
import com.themobilecompany.memorygame.model.game.LevelFactory;
import com.themobilecompany.memorygame.model.game.LevelType;
import com.themobilecompany.memorygame.model.game.PlayerMode;

import java.util.HashMap;
import java.util.Map;

public class BoardConfigurationMapper {

    private static Map<Integer, LevelType> levelMapper = new HashMap<Integer, LevelType>() {
        {
            put(R.id.radio_easy, LevelType.EASY);
            put(R.id.radio_normal, LevelType.NORMAL);
            put(R.id.radio_hard, LevelType.HARD);
        }
    };

    private static Map<Integer, PlayerMode> playerModeMapper = new HashMap<Integer, PlayerMode>() {
        {
            put(R.id.radio_single_player_mode, PlayerMode.SINGLE_PLAYER);
            put(R.id.radio_multi_player_mode, PlayerMode.MULTI_PLAYER);
        }
    };

    public static Level getLevelMode(@IdRes int radioViewId) {
        return LevelFactory.getLevel(levelMapper.get(radioViewId));
    }

    public static PlayerMode getPlayerMode(@IdRes int radioViewId) {
        return playerModeMapper.get(radioViewId);
    }

    private BoardConfigurationMapper() {
    }
}
