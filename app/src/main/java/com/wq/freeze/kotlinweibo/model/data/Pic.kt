package com.wq.freeze.kotlinweibo.model.data

/**
 * Created by wangqi on 2016/3/7.
 */
data class Pic(
        val thumbnail_pic: String?, //缩略图片地址，没有时不返回此字段
        val bmiddle_pic: String?, //中等尺寸图片地址，没有时不返回此字段
        val original_pic: String? //原始图片地址，没有时不返回此字段
)