package com.software.buy3c.api

import com.software.buy3c.api.gson.AllData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * #標題
 * #描述
 * #屬性
 * #標籤
 * #註解
 *
 * Created by Jason on 2020/10/21.
 * #主維護
 * #副維護
 */
interface IETApiService {
    @Headers("Content-Type: application/json")
    @GET("AllData.json")
    fun getAllData(): Call<AllData>
}