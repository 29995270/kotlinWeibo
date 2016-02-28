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
    fun getRecentPublicWB(@Query("access_token") accessToken: String, @Query("count") count: Int = 20, @Query("page") page: Int = 1, @Query("base_app") baseApp: Int = 0): Observable<WeiboPage>

    //返回登录用户的微博广场
    @GET("/2/statuses/home_timeline.json")
    fun getMyHomeWB(
            @Query("access_token") accessToken: String,
            @Query("since_id") sinceSecend: Long = 0,
            @Query("max_id") maxId: Long = 0,
            @Query("count") count: Int = 20,
            @Query("page") page: Int = 1,
            @Query("base_app") baseApp: Int = 0,  //是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
            @Query("feature") filterType: Int = 0, //过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
            @Query("trim_user") trimUser: Int = 0  //返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
    ): Observable<WeiboPage>

    //获取指定用户的微博
    @GET("/2/statuses/user_timeline.json")
    fun getUserWB(
            @Query("access_token") accessToken: String,
            @Query("uid") uid: Long,
            @Query("screen_name") nick: String? = null,
            @Query("since_id") sinceSecend: Long = 0,
            @Query("max_id") maxId: Long = 0,
            @Query("count") count: Int = 20,
            @Query("page") page: Int = 1,
            @Query("base_app") baseApp: Int = 0,  //是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
            @Query("feature") filterType: Int = 0, //过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
            @Query("trim_user") trimUser: Int = 0  //返回值中user字段开关，0：返回完整user字段、1：user字段仅返回user_id，默认为0
    ): Observable<WeiboPage>

    //获取用户信息
    @GET("/2/users/show.json")
    fun getUserInfo(@Query("access_token") accessToken: String, @Query("uid") uid: Long, @Query("screen_name") nick: String? = null): Observable<User>
}