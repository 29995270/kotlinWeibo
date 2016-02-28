package com.wq.freeze.kotlinweibo.extension

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialog
import android.view.View
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by wangqi on 2016/2/25.
 */
fun Context.loadColor(colorRes: Int) = ContextCompat.getColor(this, colorRes)
//fun Context.toast(toString:() -> String) = Toast.makeText(this, toString.invoke(), Toast.LENGTH_SHORT).show()
//fun Context.toast(stringRes: Int) = Toast.makeText(this, this.getString(stringRes), Toast.LENGTH_SHORT).show()

fun <T: View>Activity.lazyFind(@IdRes id: Int): ReadOnlyProperty<Activity, T> {
    return object : ReadOnlyProperty<Activity, T>{
        var view: T? = null
        override fun getValue(thisRef: Activity, property: KProperty<*>): T {
            if (view == null) {
                view = thisRef.findViewById(id) as T
            }
            return view!!
        }
    }
}

fun <T: View>Fragment.lazyFind(@IdRes id: Int): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T>{
        var view: T? = null
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            if (view == null) {
                view = thisRef.view?.findViewById(id) as T
            }
            return view!!
        }
    }
}

fun RxAppCompatActivity.postRun(delay: Long = 0, runnable: () -> Unit) {
    val decorView = this.window.decorView
    if (decorView == null) {
        aaaLoge { "may activity not init, can not post run" }
    } else {
        Observable.just(null)
                .delay(delay, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle<Nothing>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ runnable() })
    }
}

fun Activity.showAlert(message: String, title: String, rightClick: () -> Unit){
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("yes"){ dialog, which ->
            rightClick()
        }
        setNegativeButton("no", null)
    }.show()

}