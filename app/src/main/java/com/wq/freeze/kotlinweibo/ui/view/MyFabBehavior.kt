package com.wq.freeze.kotlinweibo.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.dip

/**
 * Created by wangqi on 2015/12/18.
 */
class MyFabBehavior(context: Context, attributeSet: AttributeSet): FloatingActionButton.Behavior() {

    var mAnimRunning: Boolean = false

    //intercept touch event
    override fun onInterceptTouchEvent(parent: CoordinatorLayout?, child: FloatingActionButton?, ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    //about block touch below this view
    override fun blocksInteractionBelow(parent: CoordinatorLayout?, child: FloatingActionButton?): Boolean {
        return true
    }

    //about fitSystemWindow
    override fun onApplyWindowInsets(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, insets: WindowInsetsCompat?): WindowInsetsCompat? {
        return super.onApplyWindowInsets(coordinatorLayout, child, insets)
    }

    //this child is FloatingActionButton self
    override fun onMeasureChild(parent: CoordinatorLayout?, child: FloatingActionButton?, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    //this child is FloatingActionButton self
    override fun onLayoutChild(parent: CoordinatorLayout?, child: FloatingActionButton?, layoutDirection: Int): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    //looking for app:layout_anchor  app:layout_anchorGravity  (in CoordinatorLayout.LayoutParams)
    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?) {
        super.onDependentViewRemoved(parent, child, dependency)
    }


    //define you interest scroll event(axes or other)  为了接收那个方向上的后续滚动事件必须返回true
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    //会在scrolling View获得滚动事件前调用，它允许你消费部分或者全部的事件信息  (fling 只能全部消费，或者不消费)
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
    }

    //会在scrolling View做完滚动后调用，通过回调可以知道scrolling view滚动了多少和它没有消耗的滚动事件
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && !mAnimRunning) {
            animOut(child)
        } else if (dyConsumed < 0 && !mAnimRunning) {
            animIn(child)
        }
    }

    //标志着滚动的结束 － 迎接在下一个滚动之前的onStartNestedScroll() 调用
    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, target: View?) {
        super.onStopNestedScroll(coordinatorLayout, child, target)
    }

    fun animOut(child: View?) {
        if(child != null) {
            child.animate()
                .apply {
                    translationY(child.context.dip(72).toFloat())
                    duration = 300
                    withLayer()
                    setListener(object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator?) {
                            mAnimRunning = false
                        }

                        override fun onAnimationStart(animation: Animator?) {
                            mAnimRunning = true
                        }
                    })
                }.start()
        }
    }

    fun animIn(child: View?) {
        if(child != null) {
            child.animate()
                    .apply {
                        translationY(child.context.dip(0).toFloat())
                        duration = 300
                        setListener(object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                mAnimRunning = false
                            }

                            override fun onAnimationStart(animation: Animator?) {
                                mAnimRunning = true
                            }
                        })
                    }.start()
        }
    }
}