package com.wq.freeze.kotlinweibo.model.data

/**
 * Created by wangqi on 2016/2/25.
 */
data class User(
        val id: Long,
        val idstr: String,
        val screen_name: String, //用户昵称
        val name: String, //友好显示名称
        val province: Int, //用户所在省级ID
        val city: Int, //用户所在城市ID
        val location: String, //用户所在地
        val description: String, //用户个人描述
        val url: String, //用户博客地址
        val profile_image_url: String, //用户头像地址（中图），50×50像素
        val profile_url: String, //用户的微博统一URL地址
        val domain: String, //用户的个性化域名
        val weihao: String, //用户的微号
        val gender: String, //性别，m：男、f：女、n：未知
        val followers_count: Int, //粉丝数
        val friends_count: Int, //关注数
        val statuses_count: Int, //微博数
        val favourites_count: Int, //收藏数
        val created_at: String, //用户创建（注册）时间
        val following: Boolean?,
        val allow_all_act_msg: Boolean, //是否允许所有人给我发私信，true：是，false：否
        val geo_enabled: Boolean, //是否允许标识用户的地理位置，true：是，false：否
        val verified: Boolean, //是否是微博认证用户，即加V用户，true：是，false：否
        val remark: String?, //用户备注信息，只有在查询用户关系时才返回此字段
        val status: Weibo, //用户的最近一条微博信息字段
        val allow_all_comment: Boolean, //是否允许所有人对我的微博进行评论，true：是，false：否
        val avatar_large: String, //用户头像地址（大图），180×180像素
        val avatar_hd: String, //用户头像地址（高清），高清头像原图
        val verified_reason: String, //认证原因
        val follow_me: Boolean, //该用户是否关注当前登录用户，true：是，false：否
        val online_status: Int, //用户的在线状态，0：不在线、1：在线
        val bi_followers_count: Int, //用户的互粉数
        val lang: String //用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
)