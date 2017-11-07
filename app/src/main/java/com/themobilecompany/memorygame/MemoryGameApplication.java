package com.themobilecompany.memorygame;

import android.app.Application;
import android.content.Context;

import com.themobilecompany.memorygame.injection.component.DaggerUiComponent;
import com.themobilecompany.memorygame.injection.component.UiComponent;
import com.themobilecompany.memorygame.injection.module.ApplicationModule;
import com.themobilecompany.memorygame.injection.module.NetworkModule;

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
