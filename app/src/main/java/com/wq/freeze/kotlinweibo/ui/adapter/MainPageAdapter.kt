package com.wq.freeze.kotlinweibo.ui.adapter

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import com.wq.freeze.kotlinweibo.ui.fragment.WeiBoListFragment

/**
 * Created by wangqi on 2016/2/26.
 */
class MainPageAdapter(val fm: FragmentManager, val activity: RxAppCompatActivity): FragmentStatePagerAdapter(fm) {

    override fun saveState(): Parcelable? {
        return super.saveState()
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        super.restoreState(state, loader)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "微博广场"
            1 -> "我的关注"
            2 -> "我的微博"
            else -> ""
        }
    }

    override fun getItem(p0: Int): Fragment? {
        aaaLoge { "new $p0" }
        return WeiBoListFragment().apply {
            arguments = Bundle()
        }
    }

    override fun getCount() = 3
}