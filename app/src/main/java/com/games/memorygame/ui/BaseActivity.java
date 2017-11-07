package com.themobilecompany.memorygame.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.themobilecompany.memorygame.MemoryGameApplication;
import com.themobilecompany.memorygame.injection.component.UiComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private UiComponent uiComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiComponent = MemoryGameApplication.get(this).getComponent();
    }

    public UiComponent activityComponent() {
        return uiComponent;
    }

}
