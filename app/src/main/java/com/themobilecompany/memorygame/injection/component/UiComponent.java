package com.themobilecompany.memorygame.injection.component;

import android.app.Application;
import android.content.Context;

import com.themobilecompany.memorygame.injection.ApplicationContext;
import com.themobilecompany.memorygame.injection.module.ApplicationModule;
import com.themobilecompany.memorygame.injection.module.DatabaseModule;
import com.themobilecompany.memorygame.injection.module.NetworkModule;
import com.themobilecompany.memorygame.network.MemoryGameService;
import com.themobilecompany.memorygame.ui.board.BoardActivity;
import com.themobilecompany.memorygame.ui.score.ScoreActivity;
import com.themobilecompany.memorygame.ui.welcome.WelcomeActivity;

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