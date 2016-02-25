package com.wq.freeze.kotlinweibo.extension

import android.util.Log

/**
 * Created by wangqi on 2016/2/25.
 */
fun Any?.aaaLog( log:() -> String? ){
    Log.v("AAA", log.invoke())
}