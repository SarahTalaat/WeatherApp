package com.example.weatherapplication.CurrentWeather.CurrentWeatherViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModel
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.FakeWeatherRepositoryImplementation
import com.example.weatherapplication.MainCoroutineRule
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Wind
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_City
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Clouds
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Coord
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Main
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Sys
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Weather
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_WeatherArrayList
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.Model_Forecast_Database
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentWeatherViewModelTest {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var fakeRepository: FakeWeatherRepositoryImplementation

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = CurrentWeatherViewModel(fakeRepository)
    }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun testGetForecast_FromRetrofit_InCurrentWeatherViewModel() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val lat = Utils.LAT_ALERT
        val lon = Utils.LON_EGYPT
        val units = ""
        val lang = "en"
        val appid = Utils.API_KEY

        // Call the method under test
        viewModel.getForecast_FromRetrofit_InCurrentWeatherViewModel(lat, lon,units,lang, appid)

        // Run the test
        mainCoroutineRule.runTest {
            // Advance until idle to ensure all coroutines complete
            mainCoroutineRule.advanceUntilIdle()

            // Assert the state of the forecastStateFlow
            val currentState = viewModel.forecastStateFlow_InCurrentWeatherViewModel.first()
            assertThat(currentState, `is`(instanceOf(ApiState.Success_ModelForecast_Remote_InApiState::class.java)))
        }
    }


    @Test
    fun testInsertModelForecast() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val fakeForecastDatabase = createFakeForecastDatabase()

        // Call the method under test to insert forecast
        viewModel.insertModelForecast_InCurrentWeatherViewModel(fakeForecastDatabase)

        // Ensure all tasks are executed
        mainCoroutineRule.advanceUntilIdle()

        // Assert the state after insertion
        val currentState = viewModel.forecastDatabaseStateFlowList_InCurrentWeatherViewModel.value
        assertThat(currentState, instanceOf(ApiState.Success_ModelForecast_Local_InApiState::class.java))

        val forecastDatabaseList = (currentState as ApiState.Success_ModelForecast_Local_InApiState).data
        assertThat(forecastDatabaseList.contains(fakeForecastDatabase), `is`(true))
    }

    @Test
    fun testDeleteAllModelForecast() = mainCoroutineRule.runBlockingTest {
        // Call the method under test to delete all forecast
        viewModel.deleteAllModelForecast_InCurrentWeatherViewModel()

        // Ensure all tasks are executed
        mainCoroutineRule.advanceUntilIdle()

        // Assert the state after deletion
        val currentState = viewModel.forecastDatabaseStateFlowList_InCurrentWeatherViewModel.value
        assertThat(currentState, instanceOf(ApiState.Success_ModelForecast_Local_InApiState::class.java))

        val forecastDatabaseList = (currentState as ApiState.Success_ModelForecast_Local_InApiState).data
        assertThat(forecastDatabaseList.isEmpty(), `is`(true))
    }

    private fun createFakeForecast(): Model_Forecast {
        return Model_Forecast(
            modelCity = Model_City(
                id = 1,
                name = "New York",
                modelCoord = Model_Coord(lat = 40.7128, lon = -74.0060),
                country = "US",
                population = 0,
                timezone = -14400,
                sunrise = 0,
                sunset = 0
            ),
            cnt = 1,
            modelWeatherArrayList = arrayListOf(
                Model_WeatherArrayList(
                    dt = 1,
                    modelMain = Model_Main(
                        temp = 20.0,
                        feelsLike = 18.0,
                        tempMin = 18.0,
                        tempMax = 22.0,
                        pressure = 1013,
                        seaLevel = 0,
                        grndLevel = 0,
                        humidity = 50,
                        tempKf = 0.0
                    ),
                    modelWeather = arrayListOf(
                        Model_Weather(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),
                    modelClouds = Model_Clouds(all = 0),
                    modelWind = Model_Wind(speed = 5.0, deg = 180),
                    visibility = 10000,
                    pop = 0.0f,
                    modelSys = Model_Sys(pod = "d"),
                    dtTxt = "2024-04-02 12:00:00"
                )
            )
        )
    }

    private fun createFakeForecastDatabase(): Model_Forecast_Database {
        return Model_Forecast_Database(
            modelForcast = createFakeForecast(),
            id = 1
        )
    }
}
