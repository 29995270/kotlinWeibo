package com.wq.freeze.kotlinweibo.extension

import android.util.Log

/**
 * Created by wangqi on 2016/2/25.
 */
fun<T> T.aaaLogv( log:() -> String? ){
    Log.v("AAA", log.invoke())
}
fun<T> T.aaaLoge( log:() -> String? ){
    Log.e("AAA", log.invoke())
}