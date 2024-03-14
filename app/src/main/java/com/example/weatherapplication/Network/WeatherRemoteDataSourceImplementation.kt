package com.example.productsmvvm.Network

import com.example.productsmvvm.Model.Products

private const val TAB = "AllProductsFeature"
class WeatherRemoteDataSourceImplementation private constructor() : WeatherRemoteDataSourceInterface{

    private val productsService: WeatherServiceInterface by lazy {
        RetrofitHelper.getRetrofit_Instance_InRetrofitHelper().create(WeatherServiceInterface::class.java)
    }
    override suspend fun getAllProductsOverNetwork_InRDS(): List<Products> {

        val response = productsService.getWeatherByCity_FromApiEndPoint_InWeatherService().products
        return response


    }

    companion object{
        private var instance: WeatherRemoteDataSourceImplementation?=null
        fun getProductsRemoteDataSourceImplementationInstance(): WeatherRemoteDataSourceImplementation{
            return instance?: synchronized(this){
                val temp = WeatherRemoteDataSourceImplementation()
                instance = temp
                temp
            }
        }
    }
}