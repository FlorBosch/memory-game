package com.themobilecompany.memorygame.injection.module;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.themobilecompany.memorygame.BuildConfig;
import com.themobilecompany.memorygame.injection.ApplicationContext;
import com.themobilecompany.memorygame.persistence.MemoryGameDatabase;
import com.themobilecompany.memorygame.persistence.ScoreDao;
import com.themobilecompany.memorygame.persistence.ScoreDataSource;
import com.themobilecompany.memorygame.persistence.ScoreLocalDataSource;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String DATABASE = "database_name";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName() {
        return BuildConfig.DATABASE_NAME;
    }

    @Provides
    @Singleton
    MemoryGameDatabase provideMemoryGameDatabase(@ApplicationContext Context context,
                                                 @Named(DATABASE) String databaseName) {
        return Room.databaseBuilder(context, MemoryGameDatabase.class, databaseName).build();
    }

    @Provides
    @Singleton
    ScoreDao provideScoreDao(MemoryGameDatabase memoryGameDatabase) {
        return memoryGameDatabase.scoreDao();
    }

    @Provides
    @Singleton
    public ScoreDataSource provideLocalDataSource(ScoreLocalDataSource scoreLocalDataSource) {
        return scoreLocalDataSource;
    }
}
