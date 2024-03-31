package com.example.weatherapplication.Database

import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeatherLocalDataSourceImplementation: WeatherLocalDataSourceInterface {
    private val favouriteCities: MutableList<Model_FavouriteCity> = mutableListOf()
    private val modelTimes: MutableList<Model_Time> = mutableListOf()

    // Method to set the list of favorite cities in memory for testing
    fun setFavouriteCities(cities: List<Model_FavouriteCity>) {
        favouriteCities.clear()
        favouriteCities.addAll(cities)
    }

    // Method to get the list of favorite cities stored in memory for testing
    fun getFavouriteCities(): List<Model_FavouriteCity> {
        return favouriteCities.toList()
    }

    // Method to set the list of model times in memory for testing
    fun setModelTimes(times: List<Model_Time>) {
        modelTimes.clear()
        modelTimes.addAll(times)
    }

    // Method to get the list of model times stored in memory for testing
    fun getModelTimes(): List<Model_Time> {
        return modelTimes.toList()
    }
    override suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity) {
        favouriteCities.add(city)
    }

    override suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity) {
        favouriteCities.remove(city)
    }

    override suspend fun getAllStoredFavouriteCityFromDatabase_InLDS(): Flow<List<Model_FavouriteCity>> {
        return flow {
            emit(favouriteCities.toList())
        }
    }

    override suspend fun insertModelTimeIntoDatabase_InLDS(modelTime: Model_Time) {
        modelTimes.add(modelTime)
    }

    override suspend fun deleteModelTimeFromDatabase_InLDS(modelTime: Model_Time) {
        modelTimes.remove(modelTime)
    }

    override suspend fun getAllStoredModelTimeFromDatabase_InLDS(): Flow<List<Model_Time>> {
        return flow {
            emit(modelTimes.toList())
        }
    }
}