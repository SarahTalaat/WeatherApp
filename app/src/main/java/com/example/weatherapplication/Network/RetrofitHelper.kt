package com.example.productsmvvm.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//lat=46.8182&lon=8.2275 -> Alerts
object RetrofitHelper {
    const val BASE_URL_5DAYS = "https://api.openweathermap.org/data/2.5/"
    const val BASE_URL_1CALL = "https://api.openweathermap.org/data/3.0/"

    fun getRetrofit_Instance_5Days_InRetrofitHelper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_5DAYS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofit_Instance_1Call_InRetrofitHelper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_1CALL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
