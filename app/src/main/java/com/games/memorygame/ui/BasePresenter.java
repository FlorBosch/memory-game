package com.games.memorygame.ui;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T view;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        disposables.clear();
    }

    public T getView() {
        return this.view;
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected void assertViewAttached() {
        if (!this.isViewAttached()) {
            throw new ViewNotAttachedException();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    public static class ViewNotAttachedException extends RuntimeException {
        public ViewNotAttachedException() {
            super("Call Presenter.attachView(MvpView)");
        }
    }
}
