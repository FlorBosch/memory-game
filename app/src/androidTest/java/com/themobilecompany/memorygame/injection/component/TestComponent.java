package com.themobilecompany.memorygame.injection.component;

import com.themobilecompany.memorygame.injection.module.ApplicationTestModule;
import com.themobilecompany.memorygame.ui.WelcomeActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends UiComponent {

    void inject(WelcomeActivityTest activityTest);
}
