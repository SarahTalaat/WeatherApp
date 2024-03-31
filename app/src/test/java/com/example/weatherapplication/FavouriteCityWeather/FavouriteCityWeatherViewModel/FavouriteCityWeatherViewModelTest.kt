package com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.FakeWeatherRepositoryImplementation
import com.example.weatherapplication.MainCoroutineRule
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
class FavouriteCityWeatherViewModelTest {

    private lateinit var viewModel: FavouriteCityWeatherViewModel
    private lateinit var fakeRepository: FakeWeatherRepositoryImplementation

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = FavouriteCityWeatherViewModel(fakeRepository)
    }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun testGetForecast_FromRetrofit_InFavouriteCityWeatherViewModel() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val lat = Utils.LAT_ALERT
        val lon = Utils.LON_EGYPT
        val appid = Utils.API_KEY

        // Call the method under test
        viewModel.getForecast_FromRetrofit_InFavouriteCityWeatherViewModel(lat, lon, appid)

        // Run the test
        mainCoroutineRule.runTest {
            // Advance until idle to ensure all coroutines complete
            mainCoroutineRule.advanceUntilIdle()

            // Assert the state of the forecastStateFlow
            val currentState = viewModel.forecastStateFlow_InFavouriteCityWeatherViewModel.first()
            assertThat(currentState, `is`(instanceOf(ApiState.Success_ModelForecast_InApiState::class.java)))
        }
    }

    // Add more test methods for other functionalities if needed
}
