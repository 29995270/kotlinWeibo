package com.wq.freeze.kotlinweibo.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.*
import com.wq.freeze.kotlinweibo.model.data.WeiboPage
import com.wq.freeze.kotlinweibo.model.net.ApiImpl
import com.wq.freeze.kotlinweibo.ui.adapter.WeiboListAdapter
import com.wq.freeze.kotlinweibo.ui.view.BottomOnScrollListener
import rx.Observable
import rx.subjects.PublishSubject
import kotlin.properties.Delegates

/**
 * Created by wangqi on 2016/2/26.
 */
class WeiBoListFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        val LIST_TYPE_WB_SQUARE = 0
        val LIST_TYPE_WB_FAVOR = 1
        val LIST_TYPE_WB_MINE = 2
    }

    val recyclerView by lazyFind<RecyclerView>(R.id.recycler_view)
    val refreshLayout by lazyFind<SwipeRefreshLayout>(R.id.swipe_refresh)

    var listType: Int = 0
    val pageSubject = PublishSubject.create<Int>()
    var page by Delegates.observable(0){ prop, oldPage, newPage ->
        //request new page resource
        if (newPage == 0) return@observable
        if (oldPage !== newPage || (oldPage === newPage && oldPage === 1)){
            pageSubject.onNext(newPage)
        }
    }
    lateinit var listAdapter: WeiboListAdapter
    lateinit var myLoadListener: BottomOnScrollListener.LoadListener

    override val layoutRes: Int = R.layout.fragment_weibo_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listType = arguments.getInt("list_type", 0)

        listAdapter = WeiboListAdapter(mutableListOf())
        val layoutManager = object : LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false) {
            override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
                return super.getExtraLayoutSpace(state) + 200  //to fix item requestLayout
            }
        }
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
            .doOnSubscribe {
                if (page == 1 && listAdapter.dataSrc.size == 0) view.post { refreshLayout.isRefreshing = true }
            }
            .doOnNext {
                refreshLayout.isRefreshing = false
                myLoadListener.isLoading = false
            }
            .safelySubscribeWithLifecycle(this, {
                if(it == null) {
                    aaaLoge { "error $listType" }
                    return@safelySubscribeWithLifecycle
                }
                handleWeiboPage(it)
            }, {
                aaaLoge { "error $listType" }
            })

        refreshLayout.setOnRefreshListener(this)
        val myLoadListener = MyLoadListener(false)
        recyclerView.addOnScrollListener(BottomOnScrollListener(myLoadListener))

        refreshLayout.isEnabled = true
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        aaaLoge { "$listType visible change $isVisibleToUser" }
        super.setUserVisibleHint(isVisibleToUser)
        if (page == 0 && isVisibleToUser) {
            aaaLoge { "$listType this fragment is visible $this" }
            activity.postRunImmediately {
                page = 1
            }
        }
    }

    override fun onRefresh() {
        page = 1
    }

    inner class MyLoadListener(override var isLoading: Boolean) : BottomOnScrollListener.LoadListener{
        override fun onLoad() {
            super.onLoad()
            page++
        }
    }

    private fun handleWeiboPage(weiboPage: WeiboPage) {

        if (page == 1) {
            listAdapter.refresh(weiboPage)
        } else {
            listAdapter.dataSrc.addAll(weiboPage.statuses)
            listAdapter.notifyItemInserted(listAdapter.dataSrc.size)
        }

    }
}