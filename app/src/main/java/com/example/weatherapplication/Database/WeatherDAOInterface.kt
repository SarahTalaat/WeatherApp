package com.example.productsmvvm.Database
/*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productsmvvm.Model.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDAOInterface {
    @Query("SELECT * FROM products_table ")
    fun getAllStoredProducts_InDAOInterface(): Flow<List<Products>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertProduct_InDAOInterface (products: Products) : Long

    @Delete
    suspend fun deleteProduct_InDAOInterface(products: Products): Int
}

 */