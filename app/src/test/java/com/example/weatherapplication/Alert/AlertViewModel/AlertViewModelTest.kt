package com.example.weatherapplication.Alert.AlertViewModel

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.productsmvvm.Database.AppDatabase
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Database.ModelTimeDAOInterface
import com.example.weatherapplication.FakeWeatherRepositoryImplementation
import com.example.weatherapplication.MainCoroutineRule
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlertViewModelTest {

    private lateinit var viewModel: AlertViewModel
    private lateinit var fakeRepository: FakeWeatherRepositoryImplementation
    private lateinit var database: AppDatabase
    private lateinit var modelTimeDao: ModelTimeDAOInterface
    private val testCoroutineScope = TestCoroutineScope()

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = AlertViewModel(fakeRepository)

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        modelTimeDao = database.getAllModelTime_FromDAO_InAppDatabase()
    }


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

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




    fun createFakeFavouriteCityList(id: Int): List<Model_FavouriteCity> {
        return listOf(
            Model_FavouriteCity(
                id = id,
                latitude = "40.7128",
                longitude = "-74.0060",
                cityName = "New York"
            ),
            Model_FavouriteCity(
                id = id+1,
                latitude = "34.0522",
                longitude = "-118.2437",
                cityName = "Los Angeles"
            ),
            Model_FavouriteCity(
                id = id+2,
                latitude = "41.8781",
                longitude = "-87.6298",
                cityName = "Chicago"
            )
        )
    }

    fun createFakeFavouriteCity(id: Int): Model_FavouriteCity {
        return Model_FavouriteCity(
            id = id,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )
    }

    fun createFakeModelTimeList(id: Int): List<Model_Time> {
        return listOf(
            Model_Time(
                id = id,
                latitude = "40.7128",
                longitude = "-74.0060",
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                specificTime = "12:00 PM",
                city = "New York",
                shallCardAppear = true
            ),
            Model_Time(
                id = id+1,
                latitude = "34.0522",
                longitude = "-118.2437",
                startDate = "2024-04-01",
                endDate = "2024-04-30",
                specificTime = "12:00 PM",
                city = "Los Angeles",
                shallCardAppear = true
            ),
            Model_Time(
                id = id+2,
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

    fun createFakeModelTime(id: Int): Model_Time {
        return Model_Time(
            id = id,
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
