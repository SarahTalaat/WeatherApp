package com.example.weatherapplication

import com.example.weatherapplication.Database.FakeWeatherLocalDataSourceImplementation
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast

import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.FakeWeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow


class FakeWeatherRepositoryImplementation: WeatherRepositoryInterface {


    private val fakeRemoteDataSource = FakeWeatherRemoteDataSourceImplementation()
    private val fakeLocalDataSource = FakeWeatherLocalDataSourceImplementation()

    init {
        WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
            fakeRemoteDataSource,
            fakeLocalDataSource
        )
    }

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
        return fakeLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS()
    }

    override suspend fun insertFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(city)
    }

    override suspend fun deleteFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        fakeLocalDataSource.deleteFavouriteCityFromDatabase_InLDS(city)
    }

    override suspend fun getAllStoredModelTime_FromLDS_InWeatherRepository(): Flow<List<Model_Time>> {
        return fakeLocalDataSource.getAllStoredModelTimeFromDatabase_InLDS()
    }

    override suspend fun insertModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        fakeLocalDataSource.insertModelTimeIntoDatabase_InLDS(modelTime)
    }

    override suspend fun deleteModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        fakeLocalDataSource.deleteModelTimeFromDatabase_InLDS(modelTime)
    }


    private fun createFakeFavouriteCities(): List<Model_FavouriteCity> {
        return listOf(
            Model_FavouriteCity(
                id = 1,
                latitude = "40.7128",
                longitude = "-74.0060",
                cityName = "New York"
            ),
            Model_FavouriteCity(
                id = 2,
                latitude = "34.0522",
                longitude = "-118.2437",
                cityName = "Los Angeles"
            ),
            Model_FavouriteCity(
                id = 3,
                latitude = "41.8781",
                longitude = "-87.6298",
                cityName = "Chicago"
            )
        )
    }

    private fun createFakeFavouriteCity(): Model_FavouriteCity {
        return Model_FavouriteCity(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )
    }

    private fun createFakeModelTimes(): List<Model_Time> {
        return listOf(
            Model_Time(
                id = 1,
                latitude = "40.7128",
                longitude = "-74.0060",
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                specificTime = "12:00 PM",
                city = "New York",
                shallCardAppear = true
            ),
            Model_Time(
                id = 2,
                latitude = "34.0522",
                longitude = "-118.2437",
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                specificTime = "12:00 PM",
                city = "Los Angeles",
                shallCardAppear = true
            ),
            Model_Time(
                id = 3,
                latitude = "41.8781",
                longitude = "-87.6298",
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                specificTime = "12:00 PM",
                city = "Chicago",
                shallCardAppear = true
            )
        )
    }

    private fun createFakeModelTime(): Model_Time {
        return Model_Time(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            startDate = "2024-04-01",
            endDate = "2024-04-30",
            specificTime = "12:00 PM",
            city = "New York",
            shallCardAppear = true
        )
    }
}