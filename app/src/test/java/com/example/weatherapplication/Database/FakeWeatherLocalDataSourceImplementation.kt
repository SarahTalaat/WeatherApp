package com.example.weatherapplication.Database

import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.Model_Forecast_Database
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeWeatherLocalDataSourceImplementation : WeatherLocalDataSourceInterface {
    private val _favouriteCities: MutableStateFlow<List<Model_FavouriteCity>> = MutableStateFlow(emptyList())
    private val _modelTimes: MutableStateFlow<List<Model_Time>> = MutableStateFlow(emptyList())
    private val _modelForecastDatabase: MutableStateFlow<List<Model_Forecast_Database>> = MutableStateFlow(emptyList())

    override suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity) {
        _favouriteCities.value += city
    }

    override suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity) {
        _favouriteCities.value -= city
    }

    override suspend fun getAllStoredFavouriteCityFromDatabase_InLDS(): Flow<List<Model_FavouriteCity>> {
        return _favouriteCities.asStateFlow()
    }

    override suspend fun insertModelTimeIntoDatabase_InLDS(modelTime: Model_Time) {
        _modelTimes.value += modelTime
    }

    override suspend fun deleteModelTimeFromDatabase_InLDS(modelTime: Model_Time) {
        _modelTimes.value -= modelTime
    }

    override suspend fun getAllStoredModelTimeFromDatabase_InLDS(): Flow<List<Model_Time>> {
        return _modelTimes.asStateFlow()
    }

    override suspend fun insertModelForecastIntoDatabase_InLDS(modelForecastDatabase: Model_Forecast_Database) {
        _modelForecastDatabase.value += modelForecastDatabase
    }

    override suspend fun deleteAllModelForecastFromDatabase_InLDS() {
        _modelForecastDatabase.value= emptyList()
    }

    override suspend fun getAllStoredModelForecastFromDatabase_InLDS(): Flow<List<Model_Forecast_Database>> {
        return  _modelForecastDatabase.asStateFlow()
    }
}
