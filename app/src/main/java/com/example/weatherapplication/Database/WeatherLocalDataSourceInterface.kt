package com.example.productsmvvm.Database


import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSourceInterface {

    suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun getAllStoredFavouriteCityFromDatabase_InLDS() : Flow<List<Model_FavouriteCity>>

    suspend fun insertModelTimeIntoDatabase_InLDS(modelTime: Model_Time)
    suspend fun deleteModelTimeFromDatabase_InLDS(modelTime: Model_Time)
    suspend fun getAllStoredModelTimeFromDatabase_InLDS() : Flow<List<Model_Time>>


    suspend fun insertModelForecastIntoDatabase_InLDS(modelForecast: Model_Forecast)
    suspend fun deleteAllModelForecastFromDatabase_InLDS()
    suspend fun getAllStoredModelForecastFromDatabase_InLDS() : Flow<List<Model_Forecast>>

}

