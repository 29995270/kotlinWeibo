package com.wq.freeze.kotlinweibo.ui.view

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.DraweeHolder
import com.facebook.drawee.view.MultiDraweeHolder
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import com.wq.freeze.kotlinweibo.extension.getDrawableCompat
import com.wq.freeze.kotlinweibo.model.data.Pic
import kotlin.properties.Delegates

/**
 * Created by wangqi on 2016/3/7.
 */
class NineGridImageView: View {

    lateinit var multiDraweeHolder: MultiDraweeHolder<GenericDraweeHierarchy>
    var pics by Delegates.observable(listOf<Pic>()){ prop, old, new ->
        // this block will access before onSizeChange, so post
        post{
            aaaLoge { "set pics $itemWidth"}
            layoutParams.height = when(new.size) {
                in 1..3 -> itemWidth
                in 4..6 -> itemWidth * 2
                else -> itemWidth * 3
            }
            requestLayout()
            new.forEachIndexed { i, pic ->
                val draweeHolder = multiDraweeHolder.get(i)
                val controller = Fresco.newDraweeControllerBuilder()
                        .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                            override fun onIntermediateImageFailed(id: String?, throwable: Throwable?) {
                                super.onIntermediateImageFailed(id, throwable)
                                aaaLoge { "load image onIntermediateImageFailed" }
                            }

                            override fun onSubmit(id: String?, callerContext: Any?) {
                                super.onSubmit(id, callerContext)
                                aaaLoge { "load image onSubmit" }
                            }

                            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                                super.onFinalImageSet(id, imageInfo, animatable)
                                aaaLoge { "load image onFinalImageSet" }
                            }

                            override fun onFailure(id: String?, throwable: Throwable?) {
                                super.onFailure(id, throwable)
                                aaaLoge { "load image onFailure" }
                            }

                            override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
                                super.onIntermediateImageSet(id, imageInfo)
                                aaaLoge { "load image onIntermediateImageSet" }
                            }

                            override fun onRelease(id: String?) {
                                super.onRelease(id)
                                aaaLoge { "load image onRelease" }
                            }
                        })
                        .apply {
                            setUri(pic.thumbnail_pic!!)

                            if (draweeHolder.controller != null) oldController = draweeHolder.controller
                        }
                        .build()
                draweeHolder.controller = controller
            }
        }
    }

    var mWidth = 0
    var mHeight = 0
    var itemWidth = 0

    constructor(context: Context): super(context) {
        initialize()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        initialize()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initialize()
    }

    fun initialize() {
        multiDraweeHolder = MultiDraweeHolder<GenericDraweeHierarchy>()
        val loadingDrawable = (context as Activity).getDrawableCompat(R.mipmap.bg_loading)
        for (i in 1..9) {
            val hierarchy = GenericDraweeHierarchyBuilder(resources)
                    .setPlaceholderImage(loadingDrawable)
                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                    .build()
            val draweeHolder = DraweeHolder.create(hierarchy, context)
            draweeHolder.topLevelDrawable?.callback = this
            multiDraweeHolder.add(draweeHolder)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        itemWidth = mWidth / 3
        aaaLoge { "size change $itemWidth"}
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pics.forEachIndexed { i, pic ->
            val drawable = multiDraweeHolder[i].topLevelDrawable
            aaaLoge { "drawable  ${drawable.intrinsicHeight} + ${drawable.intrinsicWidth}" }
            drawable.setBounds(0, 0, itemWidth, itemWidth)
            canvas.save()
            canvas.translate((i % 3 * itemWidth).toFloat(), (i / 3 * itemWidth).toFloat())
            drawable.draw(canvas)
            canvas.restore()
        }
    }

    override fun verifyDrawable(who: Drawable?): Boolean {

        if (multiDraweeHolder.verifyDrawable(who)) {
            return true
        }

        return super.verifyDrawable(who)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        multiDraweeHolder.onAttach()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        multiDraweeHolder.onDetach()
    }

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        multiDraweeHolder.onDetach()
    }

    override fun onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach()
        multiDraweeHolder.onAttach()
    }
}