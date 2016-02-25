package com.wq.freeze.kotlinweibo

import android.app.Application

/**
 * Created by wangqi on 2016/2/24.
 */
class App: Application() {

    companion object{
        lateinit var appContext: Application
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

}