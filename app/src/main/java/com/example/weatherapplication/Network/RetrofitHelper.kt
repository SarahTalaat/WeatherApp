package com.example.productsmvvm.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    const val BASE_URL = "api.openweathermap.org/data/2.5/"
    fun getRetrofit_Instance_InRetrofitHelper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
