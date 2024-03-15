package com.example.productsmvvm.Network

import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherArrayList

interface WeatherRemoteDataSourceInterface {

    /*
    suspend fun getAllProductsOverNetwork_InRDS(): List<Products>
     */

   // suspend fun getCurrentWeatherOverNetwork_InRDS(): Model_Forecast

    suspend fun getCod_OverNetwork_InRDS(lat: String, lon: String, appid: String): String?
    suspend fun getMessage_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getCnt_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getList_OverNetwork_InRDS(lat: String, lon: String, appid: String): ArrayList<Model_WeatherArrayList>
    suspend fun getCity_OverNetwork_InRDS(city: String, appid:String): Model_City?


}