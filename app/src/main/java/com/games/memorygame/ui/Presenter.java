package com.themobilecompany.memorygame.ui;

public interface Presenter<T extends MvpView> {

    void attachView(T view);

    void detachView();
}
