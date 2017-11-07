package com.games.memorygame.util;


import android.view.View;

public class ViewUtil {

    public static void setVisibility(boolean visible, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
