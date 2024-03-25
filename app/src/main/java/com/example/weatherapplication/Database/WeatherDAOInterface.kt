package com.example.productsmvvm.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity

import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDAOInterface {
    @Query("SELECT * FROM city_table ")
    fun getAllStoredFavouriteCity_InDAOInterface(): Flow<List<Model_FavouriteCity>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteCity_InDAOInterface (city: Model_FavouriteCity) : Long

    @Delete
    suspend fun deleteFavouriteCity_InDAOInterface(city: Model_FavouriteCity): Int
}

