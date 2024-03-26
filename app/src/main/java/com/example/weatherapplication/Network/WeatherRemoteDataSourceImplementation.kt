package com.example.productsmvvm.Network

import android.util.Log
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert

private const val TAB = "AllProductsFeature"
class WeatherRemoteDataSourceImplementation private constructor() : WeatherRemoteDataSourceInterface{

    private val weatherService: WeatherServiceInterface by lazy {
        RetrofitHelper.getRetrofit_Instance_5Days_InRetrofitHelper().create(WeatherServiceInterface::class.java)
    }

    private val alertService: WeatherServiceInterface by lazy {
        RetrofitHelper.getRetrofit_Instance_1Call_InRetrofitHelper().create(WeatherServiceInterface::class.java)
    }

    /*
    override suspend fun getAllProductsOverNetwork_InRDS(): List<Products> {

        val response = productsService.getWeatherByCity_FromApiEndPoint_InWeatherService().products
        r
        eturn response

    }
    */

    companion object{
        private var instance: WeatherRemoteDataSourceImplementation?=null
        fun getWeatherRemoteDataSourceImplementation_Instance(): WeatherRemoteDataSourceImplementation{
            return instance?: synchronized(this){
                val temp = WeatherRemoteDataSourceImplementation()
                instance = temp
                Log.i("TAG", "getCurrentWeatherRemoteDataSourceImplementation_Instance: response: " + instance)
                temp
            }
        }
    }

    override suspend fun getForecast_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): Model_Forecast? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat,lon,appid)
        Log.i("TAG", "getForecast_OverNetwork_InRDS: response: " + response)
        return response
    }



    override suspend fun getAlert_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): Model_Alert? {
        val response = alertService.getAlert_FromApiEndPoint_InWeatherService(lat,lon,appid)
        Log.i("TAG", "getAlert_OverNetwork_InRDS: " + response)
        return response
    }






/*
    override suspend fun getCod_OverNetwork_InRDS(lat: String, lon: String, appid: String): String? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).cod
        Log.i("TAG", "getCod_OverNetwork_InRDS: response: "+response)
        return response
    }

    override suspend fun getMessage_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).message
        Log.i("TAG", "getMessage_OverNetwork_InRDS: response: " + response)
        return response
    }

    override suspend fun getCnt_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).cnt
        Log.i("TAG", "getCnt_OverNetwork_InRDS: response:" +response)
        return response
    }

    override suspend fun getList_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): ArrayList<Model_WeatherArrayList> {
        val respone = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).modelWeatherArrayList
        Log.i("TAG", "getList_OverNetwork_InRDS: response: "+respone)
        return respone
    }

    override suspend fun getCity_OverNetwork_InRDS(city: String, appid: String): Model_City? {
        val response = weatherService.getWeatherByCity_FromApiEndPoint_InWeatherService(city,appid).modelCity
        Log.i("TAG", "getCity_OverNetwork_InRDS: response:  "+ response)
        return  response
    }
*/

}