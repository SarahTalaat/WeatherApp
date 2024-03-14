package com.example.productsmvvm.Database

import com.example.productsmvvm.Model.Products
import kotlinx.coroutines.flow.Flow

interface ProductsLocalDataSourceInterface {

    suspend fun insertProductIntoDatabase_InLDS(product: Products)
    suspend fun deleteProductFromDatabase_InLDS(product: Products)
    suspend fun getAllStoredProductsFromDatabase_InLDS() : Flow<List<Products>>

}