package com.wq.freeze.kotlinweibo.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.verticalLayout

/**
 * Created by wangqi on 2016/3/3.
 */
class ClickIntentActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            backgroundColor = Color.BLUE
        }
        aaaLoge { intent.toString() }
    }
}