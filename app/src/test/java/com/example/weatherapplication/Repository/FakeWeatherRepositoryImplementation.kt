package com.example.weatherapplication.Repository

import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Database.FakeWeatherLocalDataSourceImplementation
import com.example.weatherapplication.Model.AlertModel.APIModel.Alerts
import com.example.weatherapplication.Model.AlertModel.APIModel.Current
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Wind
import com.example.weatherapplication.Model.AlertModel.APIModel.Weather
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeWeatherRepositoryImplementation: WeatherRepositoryInterface {

    private val fakeRemoteDataSource = FakeWeatherRemoteDataSourceImplementation()
    private val fakeLocalDataSource = FakeWeatherLocalDataSourceImplementation()

    private val weatherRepository = WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
        fakeRemoteDataSource,
        fakeLocalDataSource
    )

    @Test
    fun `test getForecast_FromRDS_InWeatherRepository`() = runBlocking {
        val expectedForecast = Model_Forecast(
        cod = "200",
        message = 0,
        cnt = 40,
        modelWeatherArrayList = arrayListOf(
            Model_WeatherArrayList(
                dt = 1616820000,
                modelMain = Model_Main(
                    temp = 17.48,
                    feelsLike = 15.58,
                    tempMin = 17.48,
                    tempMax = 18.02,
                    pressure = 1023,
                    seaLevel = 1023,
                    grndLevel = 1021,
                    humidity = 49,
                    tempKf = -0.54
                ),
                modelWeather = arrayListOf(
                    Model_Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
                ),
                modelClouds = Model_Clouds(all = 0),
                modelWind = Model_Wind(speed = 1.62, deg = 166, gust = 2.33),
                visibility = 10000,
                pop = 0.04F,
                modelSys = Model_Sys(pod = "d"),
                dtTxt = "2021-04-05 15:00:00"
            )
        ),
        modelCity = Model_City(
            id = 5128581,
            name = "New York",
            modelCoord = Model_Coord(lat = 40.7128, lon = -74.0060),
            country = "US",
            population = 0,
            timezone = -14400,
            sunrise = 1616780874,
            sunset = 1616826647
        )
    )

        fakeRemoteDataSource.setAlert(expectedForecast)

        val result = weatherRepository.getForecast_FromRDS_InWeatherRepository(Utils.LAT_EGYPT, Utils.LON_EGYPT, Utils.API_KEY).first()

        assertEquals(expectedForecast, result)
    }

    @Test
    fun `test getAlert_FromRDS_InWeatherRepository`() = runBlocking {
        val expectedAlert = Model_Alert(
            lat = 40.7128,
            lon = -74.0060,
            timezone = "America/New_York",
            timezoneOffset = -14400,
            current = Current(
                dt = 1616800445,
                sunrise = 1616780874,
                sunset = 1616826647,
                temp = 18.7,
                feelsLike = 17.0,
                pressure = 1022,
                humidity = 49,
                dewPoint = 6.7,
                uvi = 4.75,
                clouds = 0,
                visibility = 10000,
                windSpeed = 1.54,
                windDeg = 140,
                weather = arrayListOf(
                    Weather(id = 800, main = "Clear", description = "clear sky", icon = "01d")
                )
            ),
            minutely = arrayListOf(),
            hourly = arrayListOf(),
            daily = arrayListOf(),
            alerts = arrayListOf(
                Alerts(
                    senderName = "NWS New York City, NY",
                    event = "Heat Advisory",
                    start = 1616798400,
                    end = 1616827200,
                    description = "Heat advisory remains in effect until 8 PM EDT this evening...",
                    tags = arrayListOf("Extreme heat risk")
                )
            )
        )
        fakeRemoteDataSource.setAlert(expectedAlert)


        val result = weatherRepository.getAlert_FromRDS_InWeatherRepository(Utils.LAT_EGYPT, Utils.LON_EGYPT, Utils.API_KEY).first()

        assertEquals(expectedAlert, result)
    }

    @Test
    fun `test getAllStoredFavouriteCity_FromLDS_InWeatherRepository`() = runBlocking {
        val expectedCities = listOf(
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

        fakeLocalDataSource.setFavouriteCities(expectedCities)

        val result = weatherRepository.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().first()

        assertEquals(expectedCities, result)
    }

    @Test
    fun `test insertFavouriteCity_FromLDS_InWeatherRepository`() = runBlocking {
        val cityToAdd =
            Model_FavouriteCity(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )

        weatherRepository.insertFavouriteCity_FromLDS_InWeatherRepository(cityToAdd)

        val result = fakeLocalDataSource.getFavouriteCities().first()

        assertEquals(listOf(cityToAdd), result)
    }

    @Test
    fun `test deleteFavouriteCity_FromLDS_InWeatherRepository`() = runBlocking {
        val cityToDelete =
            Model_FavouriteCity(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )
        fakeLocalDataSource.setFavouriteCities(listOf(cityToDelete))

        weatherRepository.deleteFavouriteCity_FromLDS_InWeatherRepository(cityToDelete)

        val result = fakeLocalDataSource.getFavouriteCities().first()

        assertEquals(emptyList<Model_FavouriteCity>(), result)
    }

    @Test
    fun `test getAllStoredModelTime_FromLDS_InWeatherRepository`() = runBlocking {
        val expectedModelTimes = listOf(
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
        fakeLocalDataSource.setModelTimes(expectedModelTimes)

        val result = weatherRepository.getAllStoredModelTime_FromLDS_InWeatherRepository().first()

        assertEquals(expectedModelTimes, result)
    }

    @Test
    fun `test insertModelTime_FromLDS_InWeatherRepository`() = runBlocking {
        val modelTimeToAdd =
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

        weatherRepository.insertModelTime_FromLDS_InWeatherRepository(modelTimeToAdd)

        val result = fakeLocalDataSource.getModelTimes().first()

        assertEquals(listOf(modelTimeToAdd), result)
    }

    @Test
    fun `test deleteModelTime_FromLDS_InWeatherRepository`() = runBlocking {
        val modelTimeToDelete =
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
        fakeLocalDataSource.setModelTimes(listOf(modelTimeToDelete))

        weatherRepository.deleteModelTime_FromLDS_InWeatherRepository(modelTimeToDelete)

        val result = fakeLocalDataSource.getModelTimes().first()

        assertEquals(emptyList<Model_Time>(), result)
    }

    override suspend fun getAlert_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Alert?> {
        return flow {
            emit(fakeRemoteDataSource.getAlert_OverNetwork_InRDS(lat, lon, appid))
        }
    }

    override suspend fun getForecast_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Forecast?> {
        return flow {
            emit(fakeRemoteDataSource.getForecast_OverNetwork_InRDS(lat, lon, appid))
        }
    }

    override suspend fun getAllStoredFavouriteCity_FromLDS_InWeatherRepository(): Flow<List<Model_FavouriteCity>> {
        return fakeLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS()
    }

    override suspend fun insertFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        return fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(city)
    }

    override suspend fun deleteFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        fakeLocalDataSource.deleteFavouriteCityFromDatabase_InLDS(city)
    }

    override suspend fun getAllStoredModelTime_FromLDS_InWeatherRepository(): Flow<List<Model_Time>> {
        return fakeLocalDataSource.getAllStoredModelTimeFromDatabase_InLDS()
    }

    override suspend fun insertModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        return fakeLocalDataSource.insertModelTimeIntoDatabase_InLDS(modelTime)
    }

    override suspend fun deleteModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        return fakeLocalDataSource.deleteModelTimeFromDatabase_InLDS(modelTime)
    }
}