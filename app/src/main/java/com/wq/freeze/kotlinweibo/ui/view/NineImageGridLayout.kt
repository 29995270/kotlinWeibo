package com.wq.freeze.kotlinweibo.ui.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.widget.GridLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.wq.freeze.kotlinweibo.model.data.Pic
import org.jetbrains.anko.dip
import java.lang.ref.WeakReference

/**
 * Created by wangqi on 2016/3/7.
 * 为了防止内存泄漏而使用 WeakReference ，但是会带来空指针的风险
 * bad idea
 */
class NineImageGridLayout : GridLayout {

    val imageViewList: MutableList<WeakReference<SimpleDraweeView>> = mutableListOf()

    var mWidth = 0
    var mHeight = 0
    var imageItemWidth = 0
    var imageItemHeight = 0

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        initialize(context)
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(context, attributes, defStyleAttr) {
        initialize(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        imageItemWidth = mWidth / 3 - dip(2)
        imageItemHeight = imageItemWidth
    }

    private fun initialize(context: Context) {
        for (i in 0..4) {
            imageViewList.add(WeakReference(SimpleDraweeView(context)))
        }
    }

    fun setImages(pics: Array<Pic>?) {
        if (pics == null || pics.size == 0 ) return

        val urls = mutableListOf<String>()
        pics.forEach {
            urls.add(it.thumbnail_pic!!)
        }
        setImages(urls)
    }

    fun setImages(urls: MutableList<String>?) {
        removeAllViews()

        if (urls == null || urls.size == 0) return

        urls.forEachIndexed { i, s ->
            if (i >= imageViewList.size) {
                SimpleDraweeView(context).setImageURI(Uri.parse(s), null)
                imageViewList.add(WeakReference(SimpleDraweeView(context)))
            } else {
                val tempDraweeView: SimpleDraweeView
                if (imageViewList[i].get() == null) {
                    tempDraweeView = SimpleDraweeView(context)
                    imageViewList[i] = WeakReference(tempDraweeView)
                } else {
                    tempDraweeView = imageViewList[i].get()
                }
                tempDraweeView.setImageURI(Uri.parse(s), null)
            }
        }
        layoutViews(urls)
    }

    private fun layoutViews(urls: List<String>) {
        when (urls.size) {
            0 -> layoutParams.height = 0
            in 1..3 -> layoutParams.height = mWidth / 3
            in 4..6 -> layoutParams.height = mWidth / 3 * 2
            in 7..9 -> layoutParams.height = mWidth
        }
        requestLayout()
        urls.forEachIndexed { i, s ->
            var layoutParams = imageViewList[i].get().layoutParams as GridLayout.LayoutParams?
            if (layoutParams == null) {
                layoutParams = GridLayout.LayoutParams()
                layoutParams.height = imageItemHeight
                layoutParams.width = imageItemWidth
                layoutParams.setGravity(Gravity.CENTER)
                imageViewList[i].get().layoutParams = layoutParams
            }
            layoutParams.columnSpec = GridLayout.spec(i % 3)
            layoutParams.rowSpec = GridLayout.spec(
                    if (i in 0..2) 0
                    else if (i in 3..5) 1
                    else 2
            )

            addView(imageViewList[i].get())
        }
        requestLayout()
    }

}