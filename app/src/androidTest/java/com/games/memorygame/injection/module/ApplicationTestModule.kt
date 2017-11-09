package com.games.memorygame.injection.module


import android.app.Application
import android.content.Context

import com.games.memorygame.injection.ApplicationContext
import com.games.memorygame.injection.MemoryGameServiceMock
import com.games.memorygame.injection.ScoreDataSourceMock
import com.games.memorygame.model.Photo
import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.network.ImageDownloader
import com.games.memorygame.network.MemoryGameService
import com.games.memorygame.persistence.ScoreDataSource

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationTestModule(protected val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideMemoryGameService(): MemoryGameService {
        return MemoryGameServiceMock()
    }

    @Provides
    @Singleton
    internal fun provideBoardConfiguration(): BoardConfiguration {
        return BoardConfiguration()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(): ScoreDataSource {
        return ScoreDataSourceMock()
    }

    @Provides
    internal fun provideImageDownloader(): ImageDownloader {
        return object : ImageDownloader {
            override fun downloadPhotos(photos: List<Photo>) {

            }
        }
    }

}