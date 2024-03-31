import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModel
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.FakeWeatherRepositoryImplementation
import com.example.weatherapplication.MainCoroutineRule
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
    fun setUp() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = AlertViewModel(fakeRepository)

/*
        var modelTime1 = Model_Time(
            id = 1,
            latitude = "40.7128",
            longitude = "-74.0060",
            startDate = "2024-04-01",
            endDate = "2024-04-30",
            specificTime = "12:00 PM",
            city = "New York",
            shallCardAppear = true
        )
        var modelTime2 = Model_Time(
            id = 2,
            latitude = "34.0522",
            longitude = "-118.2437",
            startDate = "2024-04-01",
            endDate = "2024-04-30",
            specificTime = "12:00 PM",
            city = "Los Angeles",
            shallCardAppear = true
        )
        var modelTime3 = Model_Time(
            id = 3,
            latitude = "41.8781",
            longitude = "-87.6298",
            startDate = "2024-04-01",
            endDate = "2024-04-30",
            specificTime = "12:00 PM",
            city = "Chicago",
            shallCardAppear = true
        )
        */

    }

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
/*
    @Test
    fun testGetAllLocalModelTimeStoredInDatabase() {
        // Prepare fake data
        val fakeModelTimes = fakeRepository.createFakeModelTimes()

        // Set up observer
        val observer = Observer<List<Model_Time>> { modelTimes ->
            // Verify that LiveData contains the expected data
            assertEquals(fakeModelTimes, modelTimes)
        }
        viewModel.alertLiveDataList_ModelTime_InAlertViewModel.observeForever(observer)

        // Call the method to get local model times
        viewModel.getAllLocalModelTime_StoredInDatabase_InAlertViewModel()

        // Clean up observer
        viewModel.alertLiveDataList_ModelTime_InAlertViewModel.removeObserver(observer)
    }
*/
}
