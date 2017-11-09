package com.games.memorygame.ui

interface Presenter<T : MvpView> {

    fun attachView(view: T)

    fun detachView()
}
