package com.wq.freeze.kotlinweibo.ui.adapter

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import com.wq.freeze.kotlinweibo.ui.fragment.WeiBoListFragment

/**
 * Created by wangqi on 2016/2/26.
 */
class MainPageAdapter(val fm: FragmentManager, val activity: RxAppCompatActivity): FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "微博广场"
            1 -> "我的关注"
            2 -> "我的微博"
            else -> ""
        }
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.setPrimaryItem(container, position, `object`)
    }

    override fun getItem(p0: Int): Fragment? {
        return WeiBoListFragment().apply {
            arguments = Bundle()
            arguments.putInt("list_type", p0)
        }
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)
        aaaLoge { "destroy item" }
    }

    override fun getCount() = 3
}