package com.nhs.mvp

// created By Abhishek Vadodariya

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BasePresenter<T>(protected open val view: T) {

    private val compositeDisposable = CompositeDisposable()


    fun addRxCall(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearAllCalls() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    abstract fun subscribe()

    fun unsubscribe() {
        clearAllCalls()
    }
}