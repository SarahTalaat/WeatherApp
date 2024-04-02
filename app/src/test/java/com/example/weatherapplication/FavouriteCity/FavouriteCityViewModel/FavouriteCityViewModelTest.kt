import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel.FavouriteCityViewModel
import com.example.weatherapplication.FakeWeatherRepositoryImplementation
import com.example.weatherapplication.MainCoroutineRule
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteCityViewModelTest {

    private lateinit var viewModel: FavouriteCityViewModel
    private lateinit var fakeRepository: FakeWeatherRepositoryImplementation
  //  private val testDispatcher = TestCoroutineDispatcher()
    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        fakeRepository = FakeWeatherRepositoryImplementation()
        viewModel = FavouriteCityViewModel(fakeRepository)
    }

    @Test
    fun testInsertFavouriteCity() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val fakeCity = createFakeFavouriteCity(1)

        // Call the method under test to insert the city
        viewModel = FavouriteCityViewModel(FakeWeatherRepositoryImplementation())
        viewModel.insertFavouriteCity_InFavouriteCityViewModel(fakeCity)

        // Ensure all tasks are executed
        mainCoroutineRule.advanceUntilIdle()

        // Assert the state after insertion
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.value
        assertThat(currentState, instanceOf(ApiState.Success_ModelFavouriteCity_Local_InApiState::class.java))

        val cities = (currentState as ApiState.Success_ModelFavouriteCity_Local_InApiState).data
        assertThat(cities.contains(fakeCity), `is`(true))
    }

    @Test
    fun testDeleteFavouriteCity() = mainCoroutineRule.runBlockingTest {
        // Set up test data
        val fakeCity = createFakeFavouriteCity(1)

        // Call the method under test to insert the city
        viewModel = FavouriteCityViewModel(FakeWeatherRepositoryImplementation())
        viewModel.insertFavouriteCity_InFavouriteCityViewModel(fakeCity)
        // Call the method under test to delete the city
        viewModel.deleteFavouriteCity_InFavouriteCityViewModel(fakeCity)

        // Ensure all tasks are executed
        mainCoroutineRule.advanceUntilIdle()

        // Assert the state after deletion
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.value
        assertThat(currentState, instanceOf(ApiState.Success_ModelFavouriteCity_Local_InApiState::class.java))

        val cities = (currentState as ApiState.Success_ModelFavouriteCity_Local_InApiState).data
        assertThat(cities.contains(fakeCity), `is`(false))
    }


    @Test
    fun testGetAllLocalFavouriteCity() = mainCoroutineRule.runBlockingTest {
        // Call the method under test
        viewModel.getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()

        // Ensure all tasks are executed
        mainCoroutineRule.advanceUntilIdle()

        // Assert the state of the favouriteCityStateFlowList
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.value
        assertThat(currentState, instanceOf(ApiState.Success_ModelFavouriteCity_Local_InApiState::class.java))
    }

/*
    @Test
    fun testInsertFavouriteCity() = runBlockingTest {
        // Set up test data
        val fakeCity = createFakeFavouriteCity(1)

        // Call the method under test to insert the city
        viewModel.insertFavouriteCity_InFavouriteCityViewModel(fakeCity)

        // Ensure all tasks are executed
        advanceUntilIdle()

        // Assert the state after insertion
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.first()
        assert(currentState is ApiState.Success_ModelFavouriteCity_Local_InApiState)

        val cities = (currentState as ApiState.Success_ModelFavouriteCity_Local_InApiState).data
        assert(cities.contains(fakeCity))
    }

    @Test
    fun testDeleteFavouriteCity() = runBlockingTest {
        // Set up test data
        val fakeCity = createFakeFavouriteCity(1)

        // Call the method under test to insert the city
        viewModel.insertFavouriteCity_InFavouriteCityViewModel(fakeCity)

        // Call the method under test to delete the city
        viewModel.deleteFavouriteCity_InFavouriteCityViewModel(fakeCity)

        // Ensure all tasks are executed
        advanceUntilIdle()

        // Assert the state after deletion
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.first()
        assert(currentState is ApiState.Success_ModelFavouriteCity_Local_InApiState)

        val cities = (currentState as ApiState.Success_ModelFavouriteCity_Local_InApiState).data
        assert(!cities.contains(fakeCity))
    }

    @Test
    fun testGetAllLocalFavouriteCity() = runBlockingTest {
        // Call the method under test
        viewModel.getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()

        // Ensure all tasks are executed
        advanceUntilIdle()

        // Assert the state of the favouriteCityStateFlowList
        val currentState = viewModel.favouriteCityStateFlowList_InFavouriteCityViewModel.first()
        assert(currentState is ApiState.Success_ModelFavouriteCity_Local_InApiState)
    }

    private fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        val testScope = TestCoroutineScope(testDispatcher)
        testScope.runBlockingTest {
            block()
        }
    }
*/
    private fun createFakeFavouriteCity(id: Int): Model_FavouriteCity {
        return Model_FavouriteCity(
            id = id,
            latitude = "40.7128",
            longitude = "-74.0060",
            cityName = "New York"
        )
    }
}
