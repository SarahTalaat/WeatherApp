package com.example.weatherapplication.Alert.AlertViewModel

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
class AlertViewModelTest {

    private lateinit var viewModel: AlertViewModel
    private lateinit var fakeRepository: FakeWeatherRepositoryImplementation

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = AlertViewModel(fakeRepository)
    }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun testGetAlert_FromRetrofit_InAlertViewModel() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val lat = Utils.LAT_ALERT
        val lon = Utils.lON_ALERT
        val appid = Utils.API_KEY

        // Call the method under test
        viewModel.getAlert_FromRetrofit_InAlertViewModel(lat, lon, appid)

        // Run the test
        mainCoroutineRule.runTest {
            // Advance until idle to ensure all coroutines complete
            mainCoroutineRule.advanceUntilIdle()

            // Assert the state of the alertStateFlow
            val currentState = viewModel.alertStateFlow_InAlertViewModel.first()
            assertThat(currentState, `is`(instanceOf(ApiState.Success_ModelAlert_Remote_InApiState::class.java)))
        }
    }


    // Add more test methods for other functionalities if needed
}
