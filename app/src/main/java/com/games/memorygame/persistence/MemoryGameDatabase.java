package com.games.memorygame.persistence;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.games.memorygame.model.Score;
import com.games.memorygame.util.Converters;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MemoryGameDatabase extends RoomDatabase {

    public abstract ScoreDao scoreDao();
}