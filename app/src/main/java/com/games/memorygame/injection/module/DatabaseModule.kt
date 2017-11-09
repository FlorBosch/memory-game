package com.games.memorygame.injection.module


import android.arch.persistence.room.Room
import android.content.Context

import com.games.memorygame.BuildConfig
import com.games.memorygame.injection.ApplicationContext
import com.games.memorygame.persistence.MemoryGameDatabase
import com.games.memorygame.persistence.ScoreDao
import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.persistence.ScoreLocalDataSource

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @Named(DATABASE)
    fun provideDatabaseName(): String = BuildConfig.DATABASE_NAME

    @Provides
    @Singleton
    fun provideMemoryGameDatabase(@ApplicationContext context: Context,
                                  @Named(DATABASE) databaseName: String): MemoryGameDatabase =
            Room.databaseBuilder(context, MemoryGameDatabase::class.java, databaseName).build()

    @Provides
    @Singleton
    fun provideScoreDao(memoryGameDatabase: MemoryGameDatabase): ScoreDao = memoryGameDatabase.scoreDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(scoreLocalDataSource: ScoreLocalDataSource): ScoreDataSource = scoreLocalDataSource

    companion object {
        const val DATABASE = "database_name"
    }
}
