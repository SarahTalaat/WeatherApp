package com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherView

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsmvvm.CurrentWeather.CurrentWeatherView.CurrentWeatherAdapter_Hour
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.CurrentWeather.CurrentWeatherView.CurrentWeatherAdapter_Day
import com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherViewModel.FavouriteCityWeatherViewModel
import com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherViewModel.FavouriteCityWeatherViewModelFactory_RDS
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_WeatherArrayList
import com.example.weatherapplication.Network.ApiState
import com.example.weatherapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.security.auth.login.LoginException

class FavouriteCityWeatherActivity : AppCompatActivity() {

    private lateinit var favouriteCityWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherActivity: FavouriteCityWeatherViewModelFactory_RDS
    private lateinit var favouriteCityWeatherViewModel_Instance_InFavouriteCityWeatherActivity: FavouriteCityWeatherViewModel
    private lateinit var recyclerView_Instance_Hour_InFavouriteCityWeatherActivity: RecyclerView
    private lateinit var adapter_Instance_Hour_InFavouriteCityWeatherActivity: CurrentWeatherAdapter_Hour
    private lateinit var layoutManager_Instance_Hour_InFavouriteCityWeatherActivity: LinearLayoutManager

    private lateinit var recyclerView_Instance_Day_InFavouriteCityWeatherActivity: RecyclerView
    private lateinit var adapter_Instance_Day_InFavouriteCityWeatherActivity: CurrentWeatherAdapter_Day
    private lateinit var layoutManager_Instance_Day_InFavouriteCityWeatherActivity: LinearLayoutManager

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var data: Uri

    lateinit var tv_date_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_country_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_weatherStatus_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_degreeOfTemprature_InFavouriteCityWeatherActivity: TextView
    lateinit var img_weatherStatus_InFavouriteCityWeatherActivity: ImageView

    lateinit var tv_pressure_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_humidity_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_wind_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_cloud_InFavouriteCityWeatherActivity: TextView
    lateinit var tv_visibiliy_InFavouriteCityWeatherActivity: TextView
    private var context_InFavouriteCityWeatherActivity: Context? = null
    lateinit var btn_back_InFavouriteCityWeatherActivity: Button
    lateinit var progressBar: ProgressBar


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_city_weather)

       var latitude_OnBundle_InFavouriteCityWeatherActivity = intent.getStringExtra(Utils.FAVOURITE_CITY_LATITUDE)
       var longitude_OnBundle_InFavouriteCityWeatherActivity = intent.getStringExtra(Utils.FAVOURITE_CITY_LONGITUDE)
      // var cityName_OnBundle_InFavouriteCityWeatherActivity = intent.getStringExtra(Utils.FAVOURITE_CITY_NAME)


        tv_date_InFavouriteCityWeatherActivity = findViewById(R.id.tv_Date_city)
        tv_country_InFavouriteCityWeatherActivity = findViewById(R.id.tv_Country_city)
        tv_weatherStatus_InFavouriteCityWeatherActivity = findViewById(R.id.tv_weatherState_city)
        tv_degreeOfTemprature_InFavouriteCityWeatherActivity = findViewById(R.id.tv_degreeOfTemprature_city)
        img_weatherStatus_InFavouriteCityWeatherActivity = findViewById(R.id.img_weathertatus_city)

        tv_pressure_InFavouriteCityWeatherActivity = findViewById(R.id.tv_pressure_value_city)
        tv_humidity_InFavouriteCityWeatherActivity = findViewById(R.id.tv_humidity_value_city)
        tv_wind_InFavouriteCityWeatherActivity = findViewById(R.id.tv_wind_value_city)
        tv_cloud_InFavouriteCityWeatherActivity = findViewById(R.id.tv_cloud_value_city)
        tv_visibiliy_InFavouriteCityWeatherActivity = findViewById(R.id.tv_visibility_value_city)

        btn_back_InFavouriteCityWeatherActivity = findViewById(R.id.btn_back)

        progressBar = findViewById(R.id.progressBar_favouriteCityWeather)
        favouriteCityWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherActivity = FavouriteCityWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getWeatherRemoteDataSourceImplementation_Instance() ,
                WeatherLocalDataSourceImplementation(this)
            )

        )

        favouriteCityWeatherViewModel_Instance_InFavouriteCityWeatherActivity = ViewModelProvider(this,favouriteCityWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherActivity).get(
            FavouriteCityWeatherViewModel::class.java)

        initUI_InFavouriteCityWeatherActivity()
        setUpRecyclerView_Hour_InFavouriteCityWeatherActivity()
        setUpRecyclerView_Day_InFavouriteCityWeatherActivity()






        lifecycleScope.launch {
            favouriteCityWeatherViewModel_Instance_InFavouriteCityWeatherActivity.forecastStateFlow_InFavouriteCityWeatherViewModel.collectLatest { result ->
                when(result){
                    is ApiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView_Instance_Day_InFavouriteCityWeatherActivity.visibility = View.GONE
                        recyclerView_Instance_Hour_InFavouriteCityWeatherActivity.visibility =View.GONE
                    }
                    is ApiState.Success_ModelForecast_InApiState -> {
                        progressBar.visibility = View.GONE
                        recyclerView_Instance_Day_InFavouriteCityWeatherActivity.visibility = View.VISIBLE
                        recyclerView_Instance_Hour_InFavouriteCityWeatherActivity.visibility =View.VISIBLE

                        adapter_Instance_Day_InFavouriteCityWeatherActivity.settingWeatherArrayList_InCurrentWeatherAdapter_Day(result.data as java.util.ArrayList<Model_WeatherArrayList>)
                        adapter_Instance_Hour_InFavouriteCityWeatherActivity.settingWeatherArrayList_InCurrentWeatherAdapter_Hour(result.data as java.util.ArrayList<Model_WeatherArrayList>)
                        adapter_Instance_Hour_InFavouriteCityWeatherActivity.notifyDataSetChanged()
                        adapter_Instance_Day_InFavouriteCityWeatherActivity.notifyDataSetChanged()


                        var dateAndTimeFromWeatherArrayList = result.data.modelWeatherArrayList.get(0).dtTxt?.split(" ")

                        Log.i("TAG", "onCreateView: weatherStatus: "+ result.data.modelWeatherArrayList.get(2).modelWeather.get(0).description)


                        var dtTxt_value = result.data.modelWeatherArrayList.get(0).dtTxt
                        Log.i("TAG", "onCreateView: Current Weather Activity: dtTxtValue : " + dtTxt_value)
                        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val date = LocalDate.parse(dtTxt_value , firstApiFormat)

                        tv_date_InFavouriteCityWeatherActivity.setText(date.dayOfWeek.toString() + System.getProperty("line.separator") +"${dateAndTimeFromWeatherArrayList?.get(0)}")

                        tv_weatherStatus_InFavouriteCityWeatherActivity.setText(result.data.modelWeatherArrayList.get(2).modelWeather.get(0).description)

                        var tempratureFehrenheit = result.data.modelWeatherArrayList.get(0).modelMain?.feelsLike
                        var tempratureCelsius = tempratureFehrenheit?.minus(273.15)
                        val tempFormated = String.format("%.2f", tempratureCelsius)
                        tv_degreeOfTemprature_InFavouriteCityWeatherActivity.setText(tempFormated+"°C")

                        var imageIconCode = result.data.modelWeatherArrayList.get(0).modelWeather.get(0).icon
                        var imageIcon = "https://openweathermap.org/img/wn/$imageIconCode@2x.png"

                        var weatherDescription = result.data.modelWeatherArrayList.get(2).modelWeather.get(0).description
                        Glide.with(this@FavouriteCityWeatherActivity)
                            .load(imageIcon)
                            .into(img_weatherStatus_InFavouriteCityWeatherActivity)


                        var cityName = result.data.modelCity?.name
                        var countryCode = result.data.modelCity?.country
                        var countryName = Locale("", countryCode).displayCountry
                        tv_country_InFavouriteCityWeatherActivity.setText(cityName + " , " + countryName)

                        var pressure = result.data.modelWeatherArrayList.get(0).modelMain?.pressure
                        var humidity = result.data.modelWeatherArrayList.get(0).modelMain?.humidity
                        var wind = result.data.modelWeatherArrayList.get(0).modelWind?.speed
                        var clouds = result.data.modelWeatherArrayList.get(0).modelClouds?.all
                        var visibility = result.data.modelWeatherArrayList.get(0).visibility

                        tv_pressure_InFavouriteCityWeatherActivity.setText(pressure.toString() +" hpa")
                        tv_humidity_InFavouriteCityWeatherActivity.setText(humidity.toString()+ " %")
                        tv_wind_InFavouriteCityWeatherActivity.setText(wind.toString()+ " m/s")
                        tv_cloud_InFavouriteCityWeatherActivity.setText(clouds.toString()+ " %")
                        tv_visibiliy_InFavouriteCityWeatherActivity.setText(visibility.toString()+ " m")

                    }
                    is ApiState.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@FavouriteCityWeatherActivity,"There is problem in the server", Toast.LENGTH_LONG).show()
                    }
                    is ApiState.Success_ModelAlert_InApiState ->{
                        Log.i("TAG", "onCreate: FavouriteCityWeatherFragment ")
                    }
                }

            }

        }







        if(latitude_OnBundle_InFavouriteCityWeatherActivity!= null && longitude_OnBundle_InFavouriteCityWeatherActivity != null){
            favouriteCityWeatherViewModel_Instance_InFavouriteCityWeatherActivity.getForecast_FromRetrofit_InFavouriteCityWeatherViewModel(latitude_OnBundle_InFavouriteCityWeatherActivity,longitude_OnBundle_InFavouriteCityWeatherActivity, Utils.API_KEY)
        }

        btn_back_InFavouriteCityWeatherActivity.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Utils.FAVOURITE_CITY_KEY,Utils.FAVOURITE_CITY_VALUE)
            startActivity(intent)
        }

    }



    private fun initUI_InFavouriteCityWeatherActivity(){
        recyclerView_Instance_Hour_InFavouriteCityWeatherActivity = findViewById(R.id.rv_hours_city)
        recyclerView_Instance_Day_InFavouriteCityWeatherActivity = findViewById(R.id.rv_days_city)
    }

    private fun setUpRecyclerView_Hour_InFavouriteCityWeatherActivity(){
        layoutManager_Instance_Hour_InFavouriteCityWeatherActivity = LinearLayoutManager(this)
        layoutManager_Instance_Hour_InFavouriteCityWeatherActivity.orientation = RecyclerView.HORIZONTAL
        adapter_Instance_Hour_InFavouriteCityWeatherActivity = CurrentWeatherAdapter_Hour(this, ArrayList())
        recyclerView_Instance_Hour_InFavouriteCityWeatherActivity.adapter = adapter_Instance_Hour_InFavouriteCityWeatherActivity
        recyclerView_Instance_Hour_InFavouriteCityWeatherActivity.layoutManager = layoutManager_Instance_Hour_InFavouriteCityWeatherActivity
    }


    private fun setUpRecyclerView_Day_InFavouriteCityWeatherActivity(){
        layoutManager_Instance_Day_InFavouriteCityWeatherActivity = LinearLayoutManager(this)
        layoutManager_Instance_Day_InFavouriteCityWeatherActivity.orientation = RecyclerView.VERTICAL
        adapter_Instance_Day_InFavouriteCityWeatherActivity = CurrentWeatherAdapter_Day(this, ArrayList())
        recyclerView_Instance_Day_InFavouriteCityWeatherActivity.adapter = adapter_Instance_Day_InFavouriteCityWeatherActivity
        recyclerView_Instance_Day_InFavouriteCityWeatherActivity.layoutManager = layoutManager_Instance_Day_InFavouriteCityWeatherActivity
    }
}