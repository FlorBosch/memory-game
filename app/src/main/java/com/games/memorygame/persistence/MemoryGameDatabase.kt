package com.games.memorygame.persistence


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import com.games.memorygame.model.Score
import com.games.memorygame.util.Converters

@Database(entities = arrayOf(Score::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MemoryGameDatabase : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao
}