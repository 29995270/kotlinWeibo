package com.wq.freeze.kotlinweibo.extension

import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by wangqi on 2016/2/25.
 */
fun <T> Observable<T>.androidSchedulers() =
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.safelySubscribeWithLifecycle( activity: RxAppCompatActivity, action1: (t: T) -> Unit, action0: (t: Throwable) -> Unit = { it.printStackTrace() } ) =
        compose(activity.bindToLifecycle<T>()).
        subscribe(
                { action1.invoke(it) },
                { action0.invoke(it) }
        )

fun <T> Observable<T>.safelySubscribe( action1: (t: T) -> Unit ) =
        subscribe(
                { action1.invoke(it) },
                { it.printStackTrace() }
        )