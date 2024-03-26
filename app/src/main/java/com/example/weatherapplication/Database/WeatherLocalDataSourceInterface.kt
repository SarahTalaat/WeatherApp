package com.example.productsmvvm.Database


import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSourceInterface {

    suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun getAllStoredFavouriteCityFromDatabase_InLDS() : Flow<List<Model_FavouriteCity>>

}

