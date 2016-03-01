package com.wq.freeze.kotlinweibo.extension

import android.support.annotation.IdRes
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewManager
import com.facebook.drawee.view.SimpleDraweeView
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by wangqi on 2016/2/26.
 */
inline fun ViewManager.appCompatButton(init: android.widget.Button.() -> Unit): android.widget.Button {
    return ankoView({ ctx -> AnkoInternals.initiateView(ctx, AppCompatButton::class.java) }) { init() }
}

inline fun ViewManager.simpleDraweeView(init: com.facebook.drawee.view.SimpleDraweeView.() -> Unit): com.facebook.drawee.view.SimpleDraweeView {
    return ankoView({ ctx -> AnkoInternals.initiateView(ctx, SimpleDraweeView::class.java) }) { init() }
}
