package com.wq.freeze.kotlinweibo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.wq.freeze.kotlinweibo.extension.aaaLogv

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

}