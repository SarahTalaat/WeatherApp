package com.example.productsmvvm.Network

import com.example.weatherapplication.Model.Model_Forecast
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model_Alert
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServiceInterface {
    @GET("forecast?")
    suspend fun getCurrentWeather_FromApiEndPoint_InWeatherService(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Utils.API_KEY
    ): Model_Forecast


    @GET("forecast?")
    suspend fun getWeatherByCity_FromApiEndPoint_InWeatherService(
        @Query("q") city: String,
        @Query("appid") appid: String = Utils.API_KEY
    ): Model_Forecast


    @GET("onecall?")
    suspend fun getAlert_FromApiEndPoint_InWeatherService(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = Utils.API_KEY
    ): Model_Alert

}