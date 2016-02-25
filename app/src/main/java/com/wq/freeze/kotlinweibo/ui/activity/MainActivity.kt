package com.wq.freeze.kotlinweibo.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLog
import com.wq.freeze.kotlinweibo.extension.safelySubscribeWithLifecycle
import com.wq.freeze.kotlinweibo.model.net.ApiImpl
import org.jetbrains.anko.find

class MainActivity : RxAppCompatActivity() {

    val toolBar by lazy { find<Toolbar>(R.id.tool_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)
        toolBar.title = "我的微博广场"
        val token = intent.extras.getString("token", "null")

        ApiImpl.instance.getRecentPublicWB(token, 1)
            .safelySubscribeWithLifecycle(this) {
                aaaLog {
                    it.toString()
                }
            }
    }
}
