package com.example.weatherapplication.Repository

import com.example.weatherapplication.Database.FakeWeatherLocalDataSourceImplementation
import com.example.weatherapplication.MainCoroutineRule
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.FakeWeatherRemoteDataSourceImplementation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplementationTest {

    var favouriteCityList = listOf(
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

    var modelTimeList = listOf(
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

    lateinit var fakeRemoteDataSource: FakeWeatherRemoteDataSourceImplementation
    lateinit var fakeLocalDataSource: FakeWeatherLocalDataSourceImplementation
    lateinit var repository: WeatherRepositoryImplementation
    lateinit var mainRule: MainCoroutineRule


    @Before
    fun setUp(){
        fakeRemoteDataSource = FakeWeatherRemoteDataSourceImplementation()
        fakeLocalDataSource = FakeWeatherLocalDataSourceImplementation()
        repository = WeatherRepositoryImplementation(fakeRemoteDataSource,fakeLocalDataSource)
        mainRule = MainCoroutineRule()
    }


    @Test
    fun insertFavoriteCity_expectTrue() = mainRule.runBlockingTest {
        // Insert a favorite city
        val cityToInsert = Model_FavouriteCity(
            id = 4,
            latitude = "51.5074",
            longitude = "-0.1278",
            cityName = "London"
        )
        repository.insertFavouriteCity_FromLDS_InWeatherRepository(cityToInsert)

        // Retrieve all favorite cities
        val retrievedCities = repository.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().first()

        // Check if the inserted city is in the list
        assertTrue(cityToInsert in retrievedCities)
    }

    @Test
    fun deleteFavoriteCity_expectTrue() = mainRule.runBlockingTest {
        // Insert a favorite city
        val cityToDelete = favouriteCityList.first()
        fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(cityToDelete)

        // Delete the favorite city
        repository.deleteFavouriteCity_FromLDS_InWeatherRepository(cityToDelete)

        // Retrieve all favorite cities
        val remainingCities = repository.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().first()

        // Check if the deleted city is no longer in the list
        assertTrue(cityToDelete !in remainingCities)
    }

    @Test
    fun retrieveAllStoredFavoriteCities_expectEqual() = mainRule.runBlockingTest {
        // Insert favorite cities
        favouriteCityList.forEach { fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(it) }

        // Retrieve all stored favorite cities
        val retrievedCities = repository.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().first()

        // Check if the retrieved list matches the inserted list
        assertEquals(favouriteCityList, retrievedCities)
    }

    @Test
    fun insertModelTime_expectTrue() = mainRule.runBlockingTest {
        // Insert a model time
        val timeToInsert = Model_Time(
            id = 4,
            latitude = "51.5074",
            longitude = "-0.1278",
            startDate = "2024-04-01",
            endDate = "2024-04-30",
            specificTime = "12:00 PM",
            city = "London",
            shallCardAppear = true
        )
        repository.insertModelTime_FromLDS_InWeatherRepository(timeToInsert)

        // Retrieve all model times
        val retrievedModelTimes = repository.getAllStoredModelTime_FromLDS_InWeatherRepository().first()

        // Check if the inserted time is in the list
        assertTrue(timeToInsert in retrievedModelTimes)
    }

    @Test
    fun deleteModelTime_expectTrue() = mainRule.runBlockingTest {
        // Insert a model time
        val timeToDelete = modelTimeList.first()
        fakeLocalDataSource.insertModelTimeIntoDatabase_InLDS(timeToDelete)

        // Delete the model time
        repository.deleteModelTime_FromLDS_InWeatherRepository(timeToDelete)

        // Retrieve all model times
        val remainingModelTimes = repository.getAllStoredModelTime_FromLDS_InWeatherRepository().first()

        // Check if the deleted time is no longer in the list
        assertTrue(timeToDelete !in remainingModelTimes)
    }

    @Test
    fun retrieveAllStoredModelTimes_ExpectEqual() = mainRule.runBlockingTest {
        // Insert model times
        modelTimeList.forEach { fakeLocalDataSource.insertModelTimeIntoDatabase_InLDS(it) }

        // Retrieve all stored model times
        val retrievedModelTimes = repository.getAllStoredModelTime_FromLDS_InWeatherRepository().first()

        // Check if the retrieved list matches the inserted list
        assertEquals(modelTimeList, retrievedModelTimes)
    }

    @Test
    fun insertIntoDatabaseUsingLocalDataSource_expectTrue() = mainRule.runBlockingTest {
        // Insert a favorite city
        val cityToInsert = Model_FavouriteCity(
            id = 4,
            latitude = "51.5074",
            longitude = "-0.1278",
            cityName = "London"
        )
        fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(cityToInsert)

        // Retrieve all favorite cities
        val retrievedCities = fakeLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS().first()

        // Check if the inserted city is in the list
        assertTrue(cityToInsert in retrievedCities)
    }

    @Test
    fun `delete from database using local data source`() = mainRule.runBlockingTest {
        // Insert a favorite city
        val cityToDelete = favouriteCityList.first()
        fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(cityToDelete)

        // Delete the favorite city
        fakeLocalDataSource.deleteFavouriteCityFromDatabase_InLDS(cityToDelete)

        // Retrieve all favorite cities
        val remainingCities = fakeLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS().first()

        // Check if the deleted city is no longer in the list
        assertTrue(cityToDelete !in remainingCities)
    }

    @Test
    fun `retrieve all stored from database using local data source`() = mainRule.runBlockingTest {
        // Insert favorite cities
        favouriteCityList.forEach { fakeLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(it) }

        // Retrieve all stored favorite cities
        val retrievedCities = fakeLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS().first()

        // Check if the retrieved list matches the inserted list
        assertEquals(favouriteCityList, retrievedCities)
    }



}

