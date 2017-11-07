package com.themobilecompany.memorygame.persistence;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.themobilecompany.memorygame.model.Score;
import com.themobilecompany.memorygame.util.Converters;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MemoryGameDatabase extends RoomDatabase {

    public abstract ScoreDao scoreDao();
}