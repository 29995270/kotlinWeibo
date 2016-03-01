package com.wq.freeze.kotlinweibo.ui.view

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import com.facebook.drawee.view.SimpleDraweeView
import com.wq.freeze.kotlinweibo.extension.getDrawableCompat

/**
 * Created by wangqi on 2016/3/1.
 */
class VDraweeView : SimpleDraweeView {

    var tipDrawable: Drawable? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){}

    constructor(context: Context): super(context) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (tipDrawable != null) {
            tipDrawable?.setBounds(0, 0, tipDrawable?.intrinsicWidth!!, tipDrawable?.intrinsicHeight!!)
            canvas.translate((width - tipDrawable?.intrinsicWidth!!).toFloat(), (height - tipDrawable?.intrinsicHeight!!).toFloat())
            tipDrawable?.draw(canvas)
        }
    }

    fun setTip(@DrawableRes id: Int) {
        val drawable = (context as Activity).getDrawableCompat(id)
        tipDrawable = drawable
        invalidate()
    }

    fun setTip(drawable: Drawable?) {
        tipDrawable = drawable
        invalidate()
    }
}