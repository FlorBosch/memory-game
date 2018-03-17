package com.games.memorygame.util

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.observeOnMainThread() =
        this.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun Action.observeOnMainThread(): Completable =
        Completable.fromAction(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.observeOnMainThread() =
        this.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
