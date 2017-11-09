package com.games.memorygame.injection.component

import android.app.Application
import android.content.Context

import com.games.memorygame.injection.ApplicationContext
import com.games.memorygame.injection.module.ApplicationModule
import com.games.memorygame.injection.module.DatabaseModule
import com.games.memorygame.injection.module.NetworkModule
import com.games.memorygame.network.MemoryGameService
import com.games.memorygame.ui.board.BoardActivity
import com.games.memorygame.ui.score.ScoreActivity
import com.games.memorygame.ui.welcome.WelcomeActivity

import javax.inject.Singleton

import dagger.Component


@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, DatabaseModule::class))
interface UiComponent {
    val memoryGameService: MemoryGameService

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun inject(appActivity: WelcomeActivity)
    fun inject(scoreActivity: ScoreActivity)
    fun inject(dashboardActivity: BoardActivity)

}