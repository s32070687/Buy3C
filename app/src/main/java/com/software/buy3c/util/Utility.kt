package com.software.buy3c.util

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/19.
 * #主維護
 * #副維護
 */
object Utility {
    @JvmStatic fun saveStringValueForKey(ctx: Context?, key: String, value: String?) {
        val editor = ctx?.getSharedPreferences(Constants.LOGIN_DATA, Context.MODE_PRIVATE)?.edit()
        editor?.putString(key, value)?.apply()
    }

    @JvmStatic fun getStringValueForKey(ctx: Context?, key: String): String {
        val mSettingSp = ctx?.getSharedPreferences(Constants.LOGIN_DATA, Context.MODE_PRIVATE)
        return mSettingSp?.getString(key, "") ?: ""
    }

    @JvmStatic fun convertGsonToString(gsonObj: Any?): String {
        val gson = Gson()
        return gson.toJson(gsonObj)
    }

    @JvmStatic fun convertStringToGsonObj(jsonStr: String?, type: Type): Any? {
        var obj: Any? = null
        try {
            val gson = Gson()
            obj = gson.fromJson<Any>(jsonStr, type)
        } catch (e: Exception) {
                e.printStackTrace()
        }
        return obj
    }
}