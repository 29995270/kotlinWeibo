package com.wq.freeze.kotlinweibo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WeiboAuthListener
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.exception.WeiboException
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLog
import com.wq.freeze.kotlinweibo.extension.loadColor
import com.wq.freeze.kotlinweibo.model.config.APP_ID
import com.wq.freeze.kotlinweibo.model.config.REDIRECT_URI
import org.jetbrains.anko.*

/**
 * Created by wangqi on 2016/2/24.
 */
class LoginActivity: AppCompatActivity() {

    lateinit var authInfo: AuthInfo
    lateinit var ssohandle: SsoHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authInfo = AuthInfo(this, APP_ID, REDIRECT_URI, "")
        ssohandle = SsoHandler(this, authInfo)

        setContentView(
                verticalLayout {
                    lparams(matchParent, matchParent)
                    backgroundColor = Color.WHITE
                    gravity = Gravity.CENTER

                    imageView {
                        imageResource = R.mipmap.logo
                    }

                    button {
                        text = "授权微博登录"
                        textColor = Color.BLACK
                        backgroundColor = loadColor(R.color.colorAccent)
                        onClick {
                            ssohandle.authorize(AuthListener())
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(40)
                    }
                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ssohandle.authorizeCallBack(requestCode, resultCode, data)
    }


    inner  class AuthListener: WeiboAuthListener{
        override fun onCancel() {
            aaaLog { "cancel" }
            toast("cancel")
        }

        override fun onWeiboException(p0: WeiboException?) {
            aaaLog { p0.toString() }
            toast("exception")
        }

        override fun onComplete(p0: Bundle?) {

            val accessToken = Oauth2AccessToken.parseAccessToken(p0)
            if (accessToken.isSessionValid) {
                toast("success")
                startActivity(intentFor<MainActivity>("token" to accessToken.token))
            } else {
                toast("get token exception")
            }
        }

    }
}