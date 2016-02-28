package com.wq.freeze.kotlinweibo.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import com.wq.freeze.kotlinweibo.extension.lazyFind
import com.wq.freeze.kotlinweibo.extension.safelySubscribeWithLifecycle
import com.wq.freeze.kotlinweibo.model.data.WeiboPage
import com.wq.freeze.kotlinweibo.model.net.ApiImpl
import com.wq.freeze.kotlinweibo.ui.adapter.WeiboListAdapter
import rx.Observable
import rx.subjects.PublishSubject
import kotlin.properties.Delegates

/**
 * Created by wangqi on 2016/2/26.
 */
class WeiBoListFragment: BaseFragment() {
    companion object{
        val LIST_TYPE_WB_SQUARE = 0
        val LIST_TYPE_WB_FAVOR = 1
        val LIST_TYPE_WB_MINE = 2
    }

    val recyclerView by lazyFind<RecyclerView>(R.id.recycler_view)
    val refreshLayout by lazyFind<SwipeRefreshLayout>(R.id.swipe_refresh)

    var listType by Delegates.notNull<Int>()
    val pageSubject = PublishSubject.create<Int>()
    var page by Delegates.observable(1){ prop, oldPage, newPage ->
        //request new page resource
        if (oldPage !== newPage || (oldPage === newPage && oldPage === 1)){
            pageSubject.onNext(newPage)
        }
    }

    override val layoutRes: Int = R.layout.fragment_weibo_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listType = arguments.getInt("list_type", 0)

        refreshLayout.isRefreshing = true

        val listAdapter = WeiboListAdapter()
        val layoutManager = LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = layoutManager

        pageSubject.asObservable()
            .flatMap {
                when(listType) {
                    LIST_TYPE_WB_SQUARE -> ApiImpl.instance.getRecentPublicWB(page)
                    LIST_TYPE_WB_FAVOR -> ApiImpl.instance.getMyHomeWB(page)
                    LIST_TYPE_WB_MINE -> ApiImpl.instance.getMyWB(page)
                    else -> Observable.just(null)
                }
            }
            .safelySubscribeWithLifecycle(this, {
                if(it == null) return@safelySubscribeWithLifecycle
                handleWeiboPage(it)
            })
        page = 1

    }

    private fun handleWeiboPage(weiboPage: WeiboPage) {
        aaaLoge { weiboPage.toString() }
    }
}