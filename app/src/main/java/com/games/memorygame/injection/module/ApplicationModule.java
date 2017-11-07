package com.themobilecompany.memorygame.injection.module;

import android.app.Application;
import android.content.Context;

import com.themobilecompany.memorygame.injection.ApplicationContext;
import com.themobilecompany.memorygame.model.game.BoardConfiguration;
import com.themobilecompany.memorygame.network.GlideImageDownloader;
import com.themobilecompany.memorygame.network.ImageDownloader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    protected final Application application;

    public ApplicationModule(Application application) {
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
    BoardConfiguration provideBoardConfiguration() {
        return new BoardConfiguration();
    }

    @Provides
    ImageDownloader provideImageDownloader(GlideImageDownloader glideImageDownloader) {
        return glideImageDownloader;
    }

}