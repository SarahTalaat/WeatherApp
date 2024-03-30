import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.productsmvvm.Database.AppDatabase
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherLocalDataSourceTest {

    private lateinit var database: AppDatabase
    private lateinit var weatherLocalDataSource: WeatherLocalDataSourceImplementation

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        weatherLocalDataSource = WeatherLocalDataSourceImplementation(context)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveFavouriteCity() = runBlocking {
        val city = Model_FavouriteCity("latitude", "longitude", "CityName")
        weatherLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(city)

        val retrievedCities = weatherLocalDataSource.getAllStoredFavouriteCityFromDatabase_InLDS().first()
        assertEquals(1, retrievedCities.size)
        val retrievedCity = retrievedCities[0]
        assertEquals(city.latitude, retrievedCity.latitude)
        assertEquals(city.longitude, retrievedCity.longitude)
        assertEquals(city.cityName, retrievedCity.cityName)
    }


    @Test
    fun testInsertAndRetrieveModelTime() = runBlocking {
        val modelTime = Model_Time("latitude", "longitude", "startDate", "endDate", "specificTime", "city", true)
        weatherLocalDataSource.insertModelTimeIntoDatabase_InLDS(modelTime)

        val retrievedModelTimes = weatherLocalDataSource.getAllStoredModelTimeFromDatabase_InLDS().first()
        assertEquals(1, retrievedModelTimes.size)

        val retrievedModelTime = retrievedModelTimes[0]
        assertEquals(modelTime.latitude, retrievedModelTime.latitude)
        assertEquals(modelTime.longitude, retrievedModelTime.longitude)
        assertEquals(modelTime.startDate, retrievedModelTime.startDate)
        assertEquals(modelTime.endDate, retrievedModelTime.endDate)
        assertEquals(modelTime.specificTime, retrievedModelTime.specificTime)
        assertEquals(modelTime.city, retrievedModelTime.city)
        assertEquals(modelTime.shallCardAppear, retrievedModelTime.shallCardAppear)
    }


    @Test
    fun testDeleteFavouriteCity() = runBlocking {
        val city = Model_FavouriteCity("latitude", "longitude", "CityName")
        weatherLocalDataSource.insertFavouriteCityIntoDatabase_InLDS(city)

        weatherLocalDataSource.deleteFavouriteCityFromDatabase_InLDS(city)

        // Retrieve cities after deletion
        val retrievedCities = database.getAllFavouriteCity_FromDAO_InAppDatabase().getAllStoredFavouriteCity_InDAOInterface().first()
        assertEquals(0, retrievedCities.size)
    }

    @Test
    fun testDeleteModelTime() = runBlocking {
        val modelTime = Model_Time("latitude", "longitude", "startDate", "endDate", "specificTime", "city", true)
        weatherLocalDataSource.insertModelTimeIntoDatabase_InLDS(modelTime)

        weatherLocalDataSource.deleteModelTimeFromDatabase_InLDS(modelTime)

        // Retrieve model times after deletion
        val retrievedModelTimes = database.getAllModelTime_FromDAO_InAppDatabase().getAllStoredModelTime_InDAOInterface().first()
        assertEquals(0, retrievedModelTimes.size)
    }
}
