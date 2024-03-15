package com.example.productsmvvm.Model

import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherArrayList

interface WeatherRepositoryInterface {

    /*
    suspend fun getAllProducts_FromRDS_InProductsRepository(): List<Products>
    suspend fun getAllStoredProducts_FromLDS_InProductsRepository(): Flow<List<Products>>
    suspend fun insertProduct_FromLDS_InProductsRepository(product: Products)
    suspend fun deleteProduct_FromLDS_InProductsRepository(product: Products)
    */

    suspend fun getCod_FromRDS_InProductsRepository(lat: String, lon: String, appid: String): String?
    suspend fun getMessage_FromRDS_InProductsRepository(lat: String, lon: String, appid: String): Int?
    suspend fun getCnt_FromRDS_InProductsRepository(lat: String, lon: String, appid: String): Int?
    suspend fun getList_FromRDS_InProductsRepository(lat: String, lon: String, appid: String): ArrayList<Model_WeatherArrayList>
    suspend fun getCity_FromRDS_InProductsRepository(city: String, appid:String): Model_City?



    /*
        suspend fun getCodOverNetwork_InRDS(lat: String, lon: String, appid: String): String?
    suspend fun getMessageOverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getCntOverNetwork_InRDS(lat: String, lon: String, appid: String): Int?
    suspend fun getListOverNetwork_InRDS(lat: String, lon: String, appid: String): ArrayList<Model_WeatherList>
    suspend fun getCityOverNetwork_InRDS(city: String, appid:String): Model_City?
     */
}