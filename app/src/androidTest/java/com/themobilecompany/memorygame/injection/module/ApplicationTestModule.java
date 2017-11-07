package com.themobilecompany.memorygame.injection.module;


import android.app.Application;
import android.content.Context;

import com.themobilecompany.memorygame.injection.ApplicationContext;
import com.themobilecompany.memorygame.injection.MemoryGameServiceMock;
import com.themobilecompany.memorygame.injection.ScoreDataSourceMock;
import com.themobilecompany.memorygame.model.game.BoardConfiguration;
import com.themobilecompany.memorygame.network.ImageDownloader;
import com.themobilecompany.memorygame.network.MemoryGameService;
import com.themobilecompany.memorygame.persistence.ScoreDataSource;

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