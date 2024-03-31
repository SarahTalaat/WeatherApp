package com.example.weatherapplication

import com.example.weatherapplication.Database.FakeWeatherLocalDataSourceImplementation
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Wind
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_City
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Clouds
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Coord
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Main
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Sys
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Weather
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_WeatherArrayList

import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.FakeWeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow


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

    override suspend fun getForecast_FromRDS_InWeatherRepository(lat: String, lon: String, appid: String): Flow<Model_Forecast?> {
        // Return a fake Model_Forecast object for testing purposes
        return flow {
            // Create fake data for the Model_City object
            val fakeCity = Model_City(
                id = 123,
                name = "City Name",
                modelCoord = Model_Coord(lat = 12.345, lon = 67.89),
                country = "Country",
                population = 100000,
                timezone = 3600,
                sunrise = 1617241418,
                sunset = 1617284895
            )

            // Create fake data for the Model_WeatherArrayList object
            val fakeWeatherList = arrayListOf<Model_WeatherArrayList>()

            // Add fake data for a single Model_WeatherArrayList object
            fakeWeatherList.add(
                Model_WeatherArrayList(
                    dt = 1617286800,
                    modelMain = Model_Main(
                        temp = 25.6,
                        feelsLike = 26.8,
                        tempMin = 24.5,
                        tempMax = 27.8,
                        pressure = 1014,
                        seaLevel = 1014,
                        grndLevel = 1013,
                        humidity = 45,
                        tempKf = -0.8
                    ),
                    modelWeather = arrayListOf(
                        Model_Weather(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),
                    modelClouds = Model_Clouds(
                        all = 0
                    ),
                    modelWind = Model_Wind(
                        speed = 3.89,
                        deg = 233
                    ),
                    visibility = 10000,
                    pop = 0.0f,
                    modelSys = Model_Sys(
                        pod = "d"
                    ),
                    dtTxt = "2021-04-01 12:00:00"
                )
            )

            // Create a fake Model_Forecast object
            val fakeForecast = Model_Forecast(
                cod = "200",
                message = 0,
                cnt = 1,
                modelWeatherArrayList = fakeWeatherList,
                modelCity = fakeCity
            )

            // Emit the fake forecast
            emit(fakeForecast)
        }
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


     fun createFakeFavouriteCities(): List<Model_FavouriteCity> {
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

     fun createFakeFavouriteCity(): Model_FavouriteCity {
        return Model_FavouriteCity(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )
    }

     fun createFakeModelTimes(): List<Model_Time> {
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

     fun createFakeModelTime(): Model_Time {
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