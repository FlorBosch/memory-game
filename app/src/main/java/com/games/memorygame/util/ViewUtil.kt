package com.games.memorygame.util


import android.view.View

object ViewUtil {

    fun setVisibility(visible: Boolean, vararg views: View) {
        views.forEach { it.visibility = if (visible) View.VISIBLE else View.GONE }
    }
}
