package com.wq.freeze.kotlinweibo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.Gravity
import android.view.View
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WeiboAuthListener
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.exception.WeiboException
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.App
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLogv
import com.wq.freeze.kotlinweibo.extension.postRun
import com.wq.freeze.kotlinweibo.extension.safelySubscribeWithLifecycle
import com.wq.freeze.kotlinweibo.model.config.APP_ID
import com.wq.freeze.kotlinweibo.model.config.AppPreference
import com.wq.freeze.kotlinweibo.model.config.REDIRECT_URI
import com.wq.freeze.kotlinweibo.model.net.ApiImpl
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import kotlin.properties.Delegates

/**
 * Created by wangqi on 2016/2/24.
 */
class LoginActivity: RxAppCompatActivity() {

    lateinit var authInfo: AuthInfo
    lateinit var ssohandle: SsoHandler
    var tokenPref by AppPreference.anyPreference(App.appContext, "token", "")
    var uidPref by AppPreference.anyPreference(App.appContext, "uid", 0L)
    var hasAuth: Boolean by Delegates.observable(false) { prop, oldBool, newBool ->
        if (!newBool) {
            authButton.visibility = View.VISIBLE
        } else {
            toast("auto login")
            postRun(1500) {
                startActivity(intentFor<MainActivity>("token" to tokenPref, "uid" to uidPref))
                finish()
            }
        }
    }

    lateinit var authButton: AppCompatButton

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

                    authButton = customView<AppCompatButton> {
                        text = "授权微博登录"
                        textColor = Color.BLACK
//                        backgroundColor = loadColor(R.color.colorAccent)
                        onClick {
                            ssohandle.authorize(AuthListener())
                        }
                        visibility = View.GONE
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(40)
                    }
                }
        )

        ApiImpl.instance.getTokenInfo(tokenPref)
            .safelySubscribeWithLifecycle(this, {
                if (it.expire_in > 1800)
                    hasAuth = true
                else
                    hasAuth = false
            }, {
                hasAuth = false
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ssohandle.authorizeCallBack(requestCode, resultCode, data)
    }


    inner  class AuthListener: WeiboAuthListener{
        override fun onCancel() {
            aaaLogv { "cancel" }
            toast("cancel")
        }

        override fun onWeiboException(p0: WeiboException?) {
            aaaLogv { p0.toString() }
            toast("exception")
        }

        override fun onComplete(p0: Bundle?) {

            val accessToken = Oauth2AccessToken.parseAccessToken(p0)
            if (accessToken.isSessionValid) {
                tokenPref = accessToken.token
                uidPref = accessToken.uid.toLong()
                toast("success")
                startActivity(intentFor<MainActivity>("token" to accessToken.token))
                finish()
            } else {
                toast("get token exception")
            }
        }

    }
}