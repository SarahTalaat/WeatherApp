package com.example.productsmvvm.Database

/*
import android.content.Context
import com.example.productsmvvm.Model.Products
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImplementation (context: Context) : WeatherLocalDataSourceInterface{
//dao
    private val weatherDAOInterface_InLDSImp: WeatherDAOInterface by lazy {
        val db: AppDatabase = AppDatabase.getAppDatabaseInstance(context)
        db.getAllProducts_FromDAO_InAppDatabase()
    }

    override suspend fun insertProductIntoDatabase_InLDS(product: Products) {
       //dao
        weatherDAOInterface_InLDSImp.insertProduct_InDAOInterface(product)
    }

    override suspend fun deleteProductFromDatabase_InLDS(product: Products) {
        //dao
        weatherDAOInterface_InLDSImp.deleteProduct_InDAOInterface(product)
    }

    override suspend fun getAllStoredProductsFromDatabase_InLDS(): Flow<List<Products>> {
        //dao
        return weatherDAOInterface_InLDSImp.getAllStoredProducts_InDAOInterface()
    }


}

 */