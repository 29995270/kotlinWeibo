package com.wq.freeze.kotlinweibo.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.dip

/**
 * Created by wangqi on 2015/12/18.
 */
class MyFabBehavior(context: Context, attributeSet: AttributeSet): FloatingActionButton.Behavior() {

    var mAnimRunning: Boolean = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        if (dyConsumed > 0 && !mAnimRunning) {
            animOut(child)
        } else if (dyConsumed < 0 && !mAnimRunning) {
            animIn(child)
        }
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