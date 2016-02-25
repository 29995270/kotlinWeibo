package com.wq.freeze.kotlinweibo.model.data

/**
 * Created by wangqi on 2016/2/25.
 */
data class WeiboPage(
    val statuses: MutableList<Weibo>,
    val previous_cursor: Long,
    val next_cursor: Long,
    val total_number: Int
)