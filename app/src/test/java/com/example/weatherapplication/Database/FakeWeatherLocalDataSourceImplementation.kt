package com.example.weatherapplication.Database

import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

class FakeWeatherLocalDataSourceImplementation: WeatherLocalDataSourceInterface {
    override suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStoredFavouriteCityFromDatabase_InLDS(): Flow<List<Model_FavouriteCity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertModelTimeIntoDatabase_InLDS(modelTime: Model_Time) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteModelTimeFromDatabase_InLDS(modelTime: Model_Time) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStoredModelTimeFromDatabase_InLDS(): Flow<List<Model_Time>> {
        TODO("Not yet implemented")
    }
}