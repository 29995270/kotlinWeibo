package com.wq.freeze.kotlinweibo.ui.view

import android.content.Context
import android.text.util.Linkify
import android.util.AttributeSet
import android.widget.TextView
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import java.util.regex.Pattern

/**
 * Created by wangqi on 2016/3/2.
 */
class LinkTextView : TextView {
    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        init()
    }

    fun init(){
        val pattern = Pattern.compile("@")
        Linkify.addLinks(this, pattern, "http://kanjian.com/aboutus/help/#help24")
    }

}