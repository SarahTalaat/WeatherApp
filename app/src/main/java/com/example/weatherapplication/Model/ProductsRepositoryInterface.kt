package com.example.productsmvvm.Model

import kotlinx.coroutines.flow.Flow

interface ProductsRepositoryInterface {

    suspend fun getAllProducts_FromRDS_InProductsRepository(): List<Products>
    suspend fun getAllStoredProducts_FromLDS_InProductsRepository(): Flow<List<Products>>
    suspend fun insertProduct_FromLDS_InProductsRepository(product: Products)
    suspend fun deleteProduct_FromLDS_InProductsRepository(product: Products)

}