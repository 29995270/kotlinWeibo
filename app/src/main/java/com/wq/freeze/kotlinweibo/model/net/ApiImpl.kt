package com.wq.freeze.kotlinweibo.model.net

import com.squareup.moshi.Moshi
import com.wq.freeze.kotlinweibo.App
import com.wq.freeze.kotlinweibo.extension.aaaLoge
import com.wq.freeze.kotlinweibo.extension.androidSchedulers
import com.wq.freeze.kotlinweibo.model.config.AppPreference
import com.wq.freeze.kotlinweibo.model.config.BASE_URI
import com.wq.freeze.kotlinweibo.model.data.TokenInfo
import com.wq.freeze.kotlinweibo.model.data.User
import com.wq.freeze.kotlinweibo.model.data.WeiboPage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable

/**
 * Created by wangqi on 2016/2/24.
 */
class ApiImpl(){
    object instance{
        private lateinit var api: Api
        private val moshi = Moshi.Builder().build()
        init {
            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        aaaLoge { it.request().url().url().toString() }
                        it.proceed(it.request())
                    }
                    .build()
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URI)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            api = retrofit.create(Api::class.java)
        }

        fun moshiInstanc() = moshi

        val token by AppPreference.readOnlyPreference(App.appContext, "token", "")
        val uid by AppPreference.readOnlyPreference(App.appContext, "uid", 0L)

//        fun authorization(): Observable<AuthorizeCode> {
//            return api.authorization(APP_ID, APP_SECRET, REDIRECT_URI)
//        }

        fun getTokenInfo(accessToken: String): Observable<TokenInfo> {
            return api.getTokenInfo(accessToken).androidSchedulers()
        }

        fun revokeOauth2(accessToken: String): Observable<Api.RevokeOauth2Result> {
            return api.revokeOauth2(accessToken).androidSchedulers()
        }

        fun getRecentPublicWB(pageIndex: Int): Observable<WeiboPage> {
            return api.getRecentPublicWB(accessToken = token, page = pageIndex).androidSchedulers()
        }

        fun getMyHomeWB(page: Int): Observable<WeiboPage>{
            return api.getMyHomeWB(accessToken = token, page = page).androidSchedulers()
        }

        fun getMyWB(page: Int): Observable<WeiboPage> {
            return api.getUserWB(accessToken = token, uid = uid, page = page).androidSchedulers()
        }

        fun getUserWB(uid: Long, page: Int): Observable<WeiboPage> {
            return api.getUserWB(accessToken = token, uid = uid, page = page).androidSchedulers()
        }

        fun getUserInfo(token: String, uid: Long, nick: String? = null): Observable<User> {
            return api.getUserInfo(token, uid, nick).androidSchedulers()
        }
    }
}