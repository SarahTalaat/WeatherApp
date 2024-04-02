package com.example.weatherapplication.Database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.productsmvvm.Database.AppDatabase
import com.example.productsmvvm.Database.WeatherDAOInterface
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@SmallTest //unit test
//Medium // integrated test
//Large //ui test
class WeatherDAOTest {

      private lateinit var database: AppDatabase
      private lateinit var weatherDao: WeatherDAOInterface

      @Before
      fun setp() {
          database = Room.inMemoryDatabaseBuilder(
              ApplicationProvider.getApplicationContext(),
              AppDatabase::class.java
          ).allowMainThreadQueries().build()
      weatherDao = database.getAllFavouriteCity_FromDAO_InAppDatabase()
      }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFavouriteCity() = runBlocking {
        val city = Model_FavouriteCity(id = 1, cityName = "New York", latitude = Utils.LAT_EGYPT.toString() , longitude = Utils.LON_EGYPT.toString())
        val result = weatherDao.insertFavouriteCity_InDAOInterface(city)
        assertThat(result, `is`(1L))
    }

    @Test
    @Throws(Exception::class)
    fun retrieveFavouriteCity() = runBlocking {
        //Given
        val city = Model_FavouriteCity(id = 1, cityName = "New York", latitude = Utils.LAT_EGYPT.toString() , longitude = Utils.LON_EGYPT.toString())
       //when
        var result = weatherDao.insertFavouriteCity_InDAOInterface(city)

        val allCities = weatherDao.getAllStoredFavouriteCity_InDAOInterface().first()
        assertThat(allCities.contains(city), `is`(true))
    }

    @Test
    @Throws(Exception::class)
    fun deleteFavouriteCity() = runBlocking {
        val city = Model_FavouriteCity(id = 1, cityName = "New York", latitude = Utils.LAT_EGYPT.toString() , longitude = Utils.LON_EGYPT.toString())
        weatherDao.insertFavouriteCity_InDAOInterface(city)

        val result = weatherDao.deleteFavouriteCity_InDAOInterface(city)
        assertThat(result, `is`(1))
    }

}