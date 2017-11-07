package com.games.memorygame.injection.module;


import android.app.Application;
import android.content.Context;

import com.games.memorygame.injection.ApplicationContext;
import com.games.memorygame.injection.MemoryGameServiceMock;
import com.games.memorygame.injection.ScoreDataSourceMock;
import com.games.memorygame.model.game.BoardConfiguration;
import com.games.memorygame.network.ImageDownloader;
import com.games.memorygame.network.MemoryGameService;
import com.games.memorygame.persistence.ScoreDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationTestModule {

    protected final Application application;

    public ApplicationTestModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    MemoryGameService provideMemoryGameService() {
        return new MemoryGameServiceMock();
    }

    @Provides
    @Singleton
    BoardConfiguration provideBoardConfiguration() {
        return new BoardConfiguration();
    }

    @Provides
    @Singleton
    public ScoreDataSource provideLocalDataSource() {
        return new ScoreDataSourceMock();
    }

    @Provides
    ImageDownloader provideImageDownloader() {
        return photos -> {
        };
    }

}