package com.games.memorygame.injection.component;

import com.games.memorygame.injection.module.ApplicationTestModule;
import com.games.memorygame.ui.WelcomeActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends UiComponent {

    void inject(WelcomeActivityTest activityTest);
}
