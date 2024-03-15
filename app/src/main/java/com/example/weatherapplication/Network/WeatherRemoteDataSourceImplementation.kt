package com.example.productsmvvm.Network

import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherList

private const val TAB = "AllProductsFeature"
class WeatherRemoteDataSourceImplementation private constructor() : WeatherRemoteDataSourceInterface{

    private val weatherService: WeatherServiceInterface by lazy {
        RetrofitHelper.getRetrofit_Instance_InRetrofitHelper().create(WeatherServiceInterface::class.java)
    }

    /*
    override suspend fun getAllProductsOverNetwork_InRDS(): List<Products> {

        val response = productsService.getWeatherByCity_FromApiEndPoint_InWeatherService().products
        return response

    }
    */

    companion object{
        private var instance: WeatherRemoteDataSourceImplementation?=null
        fun getCurrentWeatherRemoteDataSourceImplementation_Instance(): WeatherRemoteDataSourceImplementation{
            return instance?: synchronized(this){
                val temp = WeatherRemoteDataSourceImplementation()
                instance = temp
                temp
            }
        }
    }

    override suspend fun getCod_OverNetwork_InRDS(lat: String, lon: String, appid: String): String? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).cod
        return response
    }

    override suspend fun getMessage_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).message
        return response
    }

    override suspend fun getCnt_OverNetwork_InRDS(lat: String, lon: String, appid: String): Int? {
        val response = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).cnt
        return response
    }

    override suspend fun getList_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): ArrayList<Model_WeatherList> {
        val respone = weatherService.getCurrentWeather_FromApiEndPoint_InWeatherService(lat, lon, appid).modelWeatherList
        return respone
    }

    override suspend fun getCity_OverNetwork_InRDS(city: String, appid: String): Model_City? {
        val response = weatherService.getWeatherByCity_FromApiEndPoint_InWeatherService(city,appid).modelCity
        return  response
    }


}