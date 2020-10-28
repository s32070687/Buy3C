package com.software.buy3c.api

import com.software.buy3c.util.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientBuilder {

    fun createApiClient(): IETApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        return retrofit.create(IETApiService::class.java)
    }
    
}
