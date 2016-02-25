package com.wq.freeze.kotlinweibo.model.data

/**
 * Created by wangqi on 2016/2/25.
 */
data class Geo(
    val longitude: String,// 经度
    val latitude: String, //纬度
    val city: String, //城市代码
    val province: String, //省份代码
    val city_name: String,
    val province_name: String,
    val address: String?,
    val pinyin: String?,
    val more: String?

)