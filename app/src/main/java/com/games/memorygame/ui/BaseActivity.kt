package com.games.memorygame.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.games.memorygame.MemoryGameApplication
import com.games.memorygame.injection.component.UiComponent

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var uiComponent: UiComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiComponent = MemoryGameApplication.get(this).component
    }

    fun activityComponent(): UiComponent = uiComponent

}
