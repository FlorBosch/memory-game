package com.games.memorygame.ui;

public interface Presenter<T extends MvpView> {

    void attachView(T view);

    void detachView();
}
