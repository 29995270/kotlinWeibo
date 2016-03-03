package com.wq.freeze.kotlinweibo.extension

import android.support.annotation.IdRes
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.util.Patterns
import android.view.View
import android.view.ViewManager
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals
import java.util.regex.Pattern
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

val pattern1 = Pattern.compile("#[\\S]+#")
val pattern2 = Patterns.WEB_URL
val pattern3 = Pattern.compile("@[\u4e00-\u9fa5\\w_-]+")
val scheme1 = "kotlin:\\"
val scheme3 = "kotlin:\\"

fun TextView.setWeiboContent(content: CharSequence) {
    text = content
    Linkify.addLinks(this, Linkify.WEB_URLS)
    Linkify.addLinks(this, pattern1, scheme1)
    Linkify.addLinks(this, pattern3, scheme3)
}