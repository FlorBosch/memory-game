package com.games.memorygame

import android.app.Application
import android.content.Context

import com.games.memorygame.injection.component.DaggerUiComponent
import com.games.memorygame.injection.component.UiComponent
import com.games.memorygame.injection.module.ApplicationModule
import com.games.memorygame.injection.module.NetworkModule

import timber.log.Timber

class MemoryGameApplication : Application() {

    var component: UiComponent? = null

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        component = DaggerUiComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule(cacheDir))
                .build()
    }

    companion object {

        operator fun get(context: Context): MemoryGameApplication {
            return context.applicationContext as MemoryGameApplication
        }
    }
}
