package com.example.productsmvvm.Network

import com.example.productsmvvm.Model.Products

interface ProductsRemoteDataSourceInterface {

    suspend fun getAllProductsOverNetwork_InRDS(): List<Products>

}