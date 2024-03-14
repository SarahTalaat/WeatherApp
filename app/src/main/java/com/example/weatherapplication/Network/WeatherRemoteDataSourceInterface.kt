package com.example.productsmvvm.Network

import com.example.productsmvvm.Model.Products

interface WeatherRemoteDataSourceInterface {

    suspend fun getAllProductsOverNetwork_InRDS(): List<Products>

}