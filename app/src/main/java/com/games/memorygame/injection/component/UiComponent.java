package com.games.memorygame.injection.component;

import android.app.Application;
import android.content.Context;

import com.games.memorygame.injection.ApplicationContext;
import com.games.memorygame.injection.module.ApplicationModule;
import com.games.memorygame.injection.module.DatabaseModule;
import com.games.memorygame.injection.module.NetworkModule;
import com.games.memorygame.network.MemoryGameService;
import com.games.memorygame.ui.board.BoardActivity;
import com.games.memorygame.ui.score.ScoreActivity;
import com.games.memorygame.ui.welcome.WelcomeActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DatabaseModule.class})
public interface UiComponent {

    @ApplicationContext
    Context context();
    Application application();
    MemoryGameService getMemoryGameService();

    void inject(WelcomeActivity appActivity);
    void inject(ScoreActivity scoreActivity);
    void inject(BoardActivity dashboardActivity);

}