package com.example.productsmvvm.Network

import com.example.productsmvvm.Model.ProductsResponse
import retrofit2.http.GET

interface ProductsServiceInterface {
    @GET("forecast?")
    suspend fun getAllProducts_FromApiEndPoint_InProductsService(): ProductsResponse
}