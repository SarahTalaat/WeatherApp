package com.example.weatherapplication.Repository

import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {

    /*
    suspend fun getAllProducts_FromRDS_InProductsRepository(): List<Products>
    suspend fun getAllStoredProducts_FromLDS_InProductsRepository(): Flow<List<Products>>
    suspend fun insertProduct_FromLDS_InProductsRepository(product: Products)
    suspend fun deleteProduct_FromLDS_InProductsRepository(product: Products)
    */
/*
    suspend fun getCod_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): String?
    suspend fun getMessage_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): Int?
    suspend fun getCnt_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): Int?
    suspend fun getList_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): ArrayList<Model_WeatherArrayList>
    suspend fun getCity_FromRDS_InWeatherRepository(city: String, appid:String): Model_City?

*/


    suspend fun getAlert_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): Model_Alert?
    suspend fun getForecast_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): Model_Forecast?
    suspend fun getAllStoredFavouriteCity_FromLDS_InWeatherRepository(): Flow<List<Model_FavouriteCity>>
    suspend fun insertFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity)
    suspend fun deleteFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity)



    suspend fun getAllStoredModelTime_FromLDS_InWeatherRepository(): Flow<List<Model_Time>>
    suspend fun insertModelTime_FromLDS_InWeatherRepository(time: Model_Time)
    suspend fun deleteModelTime_FromLDS_InWeatherRepository(time: Model_Time)

    /*
        suspend fun getCodOverNetwork_InRDS(lat: String, lon: String, appid: String): String?
    suspend fun getMessageOverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getCntOverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getListOverNetwork_InRDS(lat: String, lon: String, appid: String): ArrayList<Model_WeatherList>
    suspend fun getCityOverNetwork_InRDS(city: String, appid:String): Model_City?
     */
}