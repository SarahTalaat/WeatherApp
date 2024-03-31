package com.example.weatherapplication.Repository

import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepositoryImplementation: WeatherRepositoryInterface {
    override suspend fun getAlert_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Alert?> {
        TODO("Not yet implemented")
    }

    override suspend fun getForecast_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Forecast?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStoredFavouriteCity_FromLDS_InWeatherRepository(): Flow<List<Model_FavouriteCity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStoredModelTime_FromLDS_InWeatherRepository(): Flow<List<Model_Time>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        TODO("Not yet implemented")
    }
}