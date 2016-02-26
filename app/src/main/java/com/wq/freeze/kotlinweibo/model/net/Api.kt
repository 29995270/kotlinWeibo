package com.wq.freeze.kotlinweibo.model.net

import com.wq.freeze.kotlinweibo.model.data.TokenInfo
import com.wq.freeze.kotlinweibo.model.data.User
import com.wq.freeze.kotlinweibo.model.data.WeiboPage
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

/**
 * Created by wangqi on 2016/2/24.
 */
interface  Api {
//    @POST("/oauth2/authorize")
//    fun authorization(@Query("client_id") appId: String, @Query("client_secret") appSecret: String, @Query("redirect_uri") redirectUri: String, @Query("display") display: String = "mobile"): Observable<AuthorizeCode>

    @POST("/oauth2/get_token_info")
    fun getTokenInfo(@Query("access_token") accessToken: String): Observable<TokenInfo>

    @GET("/oauth2/revokeoauth2")
    fun revokeOauth2(@Query("access_token") accessToken: String): Observable<RevokeOauth2Result>

    data class RevokeOauth2Result(val result: String)

    //返回最新的公共微博
    @GET("/2/statuses/public_timeline.json")
    fun getRecentPublicWB(@Query("access_token") accessToken: String, @Query("count") count: Int = 3, @Query("page") page: Int = 1, @Query("base_app") baseApp: Int = 0): Observable<WeiboPage>

    //获取用户信息
    @GET("/2/users/show.json")
    fun getUserInfo(@Query("access_token") accessToken: String, @Query("uid") uid: Long, @Query("screen_name") nick: String? = null): Observable<User>
}