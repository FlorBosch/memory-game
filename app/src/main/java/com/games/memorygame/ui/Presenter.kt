package com.games.memorygame.ui

interface Presenter<in T : MvpView> {

    fun attachView(view: T)

    fun detachView()
}
