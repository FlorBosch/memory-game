package com.games.memorygame.model.game;


public class LevelFactory {

    public static Level getLevel(LevelType type) {
        switch (type) {
            case EASY:
                return new Level(3, 2, 40, 10);
            case NORMAL:
                return new Level(4, 3, 80, 20);
            case HARD:
                return new Level(5, 4, 120, 30);
            default:
                return new Level(3, 2, 40, 10);
        }
    }

    private LevelFactory() {
    }
}
