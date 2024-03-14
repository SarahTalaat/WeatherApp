package com.example.productsmvvm.Database

import android.content.Context
import com.example.productsmvvm.Model.Products
import kotlinx.coroutines.flow.Flow

class ProductsLocalDataSourceImplementation (context: Context) : ProductsLocalDataSourceInterface{
//dao
    private val productsDAOInterface_InLDSImp: ProductsDAOInterface by lazy {
        val db: AppDatabase = AppDatabase.getAppDatabaseInstance(context)
        db.getAllProducts_FromDAO_InAppDatabase()
    }

    override suspend fun insertProductIntoDatabase_InLDS(product: Products) {
       //dao
        productsDAOInterface_InLDSImp.insertProduct_InDAOInterface(product)
    }

    override suspend fun deleteProductFromDatabase_InLDS(product: Products) {
        //dao
        productsDAOInterface_InLDSImp.deleteProduct_InDAOInterface(product)
    }

    override suspend fun getAllStoredProductsFromDatabase_InLDS(): Flow<List<Products>> {
        //dao
        return productsDAOInterface_InLDSImp.getAllStoredProducts_InDAOInterface()
    }


}