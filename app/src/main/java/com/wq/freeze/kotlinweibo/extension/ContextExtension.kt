package com.wq.freeze.kotlinweibo.extension

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by wangqi on 2016/2/25.
 */
fun Context.loadColor(colorRes: Int) = ContextCompat.getColor(this, colorRes)
//fun Context.toast(toString:() -> String) = Toast.makeText(this, toString.invoke(), Toast.LENGTH_SHORT).show()
//fun Context.toast(stringRes: Int) = Toast.makeText(this, this.getString(stringRes), Toast.LENGTH_SHORT).show()
