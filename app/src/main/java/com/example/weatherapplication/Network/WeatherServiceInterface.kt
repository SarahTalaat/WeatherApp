package com.example.productsmvvm.Network

import com.example.weatherapplication.Model.Forecast
import com.example.weatherapplication.Constants.Utils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceInterface {
    @GET("forecast?")
    suspend fun getWeatherByCity_FromApiEndPoint_InWeatherService(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Utils.API_KEY
    ): Call<Forecast>


    @GET("forecast?")
    suspend fun getWeatherByCity_FromApiEndPoint_InWeatherService(
        @Query("q") city: String,
        @Query("appid") appid: String = Utils.API_KEY
    ): Call<Forecast>

}