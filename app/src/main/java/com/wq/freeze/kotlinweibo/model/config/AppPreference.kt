package com.wq.freeze.kotlinweibo.model.config

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by wangqi on 2016/2/26.
 */
object AppPreference{
    fun <T>anyPreference(context: Context, name: String, default: T) =
            AnyPreference(context, name, default)
}

class AnyPreference<T>(val context: Context, val name: String, val default: T): ReadWriteProperty<Any?, T> {

    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when(value) {
            is Long -> prefs.edit().putLong(name, value).apply()
            is Boolean -> prefs.edit().putBoolean(name, value).apply()
            is Float -> prefs.edit().putFloat(name, value).apply()
            is Int -> prefs.edit().putInt(name, value).apply()
            is String -> prefs.edit().putString(name, value).apply()
            else -> {
                throw RuntimeException("can not save type ${value.toString()}")
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when(default) {
            is Long -> prefs.getLong(name, default)
            is Boolean -> prefs.getBoolean(name, default)
            is Float -> prefs.getFloat(name, default)
            is Int -> prefs.getInt(name, default)
            is String -> prefs.getString(name, default)
            else -> {
                throw RuntimeException("can not get type ${default.toString()}")
            }
        } as T
    }
}