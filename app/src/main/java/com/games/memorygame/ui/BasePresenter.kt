package com.games.memorygame.ui

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BasePresenter<T : MvpView> : Presenter<T> {

    var view: T? = null
        private set
    private val disposables = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = view != null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        disposables.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected fun assertViewAttached() {
        if (!this.isViewAttached) throw ViewNotAttachedException()
    }

    class ViewNotAttachedException : RuntimeException("Call Presenter.attachView(MvpView)")
}
