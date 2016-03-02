package com.wq.freeze.kotlinweibo.ui.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wq.freeze.kotlinweibo.extension.aaaLogv

/**
 * Created by wangqi on 2016/3/2.
 */
class BottomLoadListener(val loadListener: LoadListener): RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        checkEndOffset(recyclerView, dx, dy)
    }

    fun checkEndOffset(recyclerView: RecyclerView, dx: Int, dy: Int) {

        if (recyclerView.layoutManager is LinearLayoutManager && !(recyclerView.layoutManager as LinearLayoutManager).reverseLayout && dy > 0) {

            val visibleItemCount = recyclerView.childCount
            val totalItemCount = recyclerView.layoutManager.itemCount
            var firstVisibleItemPosition = 0
            if (recyclerView.layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }

            if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + 4)) {
                if (!loadListener.isLoading) {
                    loadListener.onLoad()
                }
            }
        }

    }

    interface LoadListener{

        var isLoading: Boolean

        fun onLoad(){
            isLoading = true
        }
    }
}