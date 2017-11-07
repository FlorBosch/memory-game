package com.games.memorygame;

import android.app.Application;
import android.content.Context;

import com.games.memorygame.injection.component.DaggerUiComponent;
import com.games.memorygame.injection.component.UiComponent;
import com.games.memorygame.injection.module.ApplicationModule;
import com.games.memorygame.injection.module.NetworkModule;

import timber.log.Timber;

public class MemoryGameApplication extends Application {

    private UiComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        applicationComponent = DaggerUiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(getCacheDir()))
                .build();
    }

    public static MemoryGameApplication get(Context context) {
        return (MemoryGameApplication) context.getApplicationContext();
    }

    public UiComponent getComponent() {
        return applicationComponent;
    }

    public void setComponent(UiComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
