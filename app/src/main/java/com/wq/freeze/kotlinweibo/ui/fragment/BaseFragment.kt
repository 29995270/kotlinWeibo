package com.wq.freeze.kotlinweibo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment
import com.wq.freeze.kotlinweibo.extension.aaaLogv
import com.wq.freeze.kotlinweibo.extension.safelySubscribeWithLifecycle
import com.wq.freeze.kotlinweibo.model.data.User
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

/**
 * Created by wangqi on 2016/2/26.
 */
abstract class BaseFragment: RxFragment() {
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loaderManager.initLoader(0, null, object : LoaderManager.LoaderCallbacks<User>{
            override fun onLoaderReset(p0: Loader<User>?) {
            }

            override fun onLoadFinished(p0: Loader<User>?, p1: User?) {
            }

            override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<User>? {
                return null
            }

        }).startLoading()
    }
}