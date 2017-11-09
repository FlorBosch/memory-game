package com.games.memorygame.injection.module

import android.app.Application
import android.content.Context

import com.games.memorygame.injection.ApplicationContext
import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.network.GlideImageDownloader
import com.games.memorygame.network.ImageDownloader

import javax.inject.Singleton

import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(protected val application: Application) {

    @Provides
    fun provideApplication(): Application = application

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideBoardConfiguration(): BoardConfiguration = BoardConfiguration()

    @Provides
    fun provideImageDownloader(downloader: GlideImageDownloader): ImageDownloader = downloader

}