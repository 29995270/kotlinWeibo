package com.wq.freeze.kotlinweibo

import android.app.Activity
import android.app.Application
import android.content.AsyncTaskLoader
import android.content.Context
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.wq.freeze.kotlinweibo.extension.aaaLogv
import com.wq.freeze.kotlinweibo.model.data.User

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
        Fresco.initialize(this);
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
//            override fun onActivityStarted(p0: Activity?) {
//                aaaLogv { "$p0 started" }
//            }
//
//            override fun onActivityStopped(p0: Activity?) {
//                aaaLogv { "$p0 stopped" }
//            }
//
//            override fun onActivityResumed(p0: Activity?) {
//                aaaLogv { "$p0 resumed" }
//            }
//
//            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
//                aaaLogv { "$p0 saveInstance" }
//            }
//
//            override fun onActivityDestroyed(p0: Activity?) {
//                aaaLogv { "$p0 destroyed" }
//            }
//
//            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
//                aaaLogv { "$p0 created" }
//            }
//
//            override fun onActivityPaused(p0: Activity?) {
//                aaaLogv { "$p0 paused" }
//            }
//
//        })
    }


    class MyAsyncLoader(context: Context): AsyncTaskLoader<User>(context) {

        var user: User? = null

        override fun loadInBackground(): User? {
            context
            return null
        }

        override fun onStartLoading() {
            // Notify the loader to reload the data
            onContentChanged()

            if (user == null)
                forceLoad()
            else
                deliverResult(user)
        }

        override fun deliverResult(data: User?) {
            super.deliverResult(data)
            var name = fun() {
                println("name")
            }
            name()

            var value = fun(name: String) = name.length
        }

        override fun onReset() {
            super.onReset()
        }

    }

}