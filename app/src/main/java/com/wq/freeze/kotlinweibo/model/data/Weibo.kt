package com.wq.freeze.kotlinweibo.model.data

import java.util.regex.Pattern

/**
 * Created by wangqi on 2016/2/25.
 */
data class Weibo(
    val created_at: String,
    val id: Long,
    val mid: Long,
    val idstr: String,//字符串型的微博ID
    val text: String, //微博信息内容
    var source: String, //微博来源
    val favorited: Boolean,
    val truncated: Boolean, //是否被截断，true：是，false：否
    val in_reply_to_status_id: String?,
    val in_reply_to_user_id: String?,
    val in_reply_to_screen_name: String?,
    val thumbnail_pic: String?, //缩略图片地址，没有时不返回此字段
    val bmiddle_pic: String?, //中等尺寸图片地址，没有时不返回此字段
    val original_pic: String?, //原始图片地址，没有时不返回此字段
    val geo: Geo?, //地理信息
    val user: User, //微博作者的用户信息字段
    val retweeted_status: Weibo?, //被转发的原微博信息字段，当该微博为转发微博时返回
    val reposts_count: Int, //转发数
    val comments_count: Int, //评论数
    val attitudes_count: Int, //表态数
    val mlevel: Int?
//    val visible: Any?,

)