package com.example.weatherapplication.CurrentWeather.CurrentWeatherView
/*
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModel
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModelFactory_RDS
import com.example.productsmvvm.CurrentWeather.CurrentWeatherView.CurrentWeatherAdapter_Hour
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation


class FavouriteCityWeatherFragment2 : Fragment() {

    private lateinit var currentWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherFragment: CurrentWeatherViewModelFactory_RDS
    private lateinit var currentWeatherViewModel_Instance_InCurrentWeatherFragmet: CurrentWeatherViewModel
    private lateinit var recyclerView_Instance_Hour_InFavouriteCityWeatherFragment: RecyclerView
    private lateinit var adapter_Instance_Hour_InFavouriteCityWeatherFragment: CurrentWeatherAdapter_Hour
    private lateinit var layoutManager_Instance_Hour_InFavouriteCityWeatherFragment: LinearLayoutManager

    private lateinit var recyclerView_Instance_Day_InFavouriteCityWeatherFragment: RecyclerView
    private lateinit var adapter_Instance_Day_InFavouriteCityWeatherFragment: CurrentWeatherAdapter_Day
    private lateinit var layoutManager_Instance_Day_InFavouriteCityWeatherFragment: LinearLayoutManager

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var data: Uri

    lateinit var tv_date_InFavouriteCityWeatherFragment:TextView
    lateinit var tv_country_InFavouriteCityWeatherFragment: TextView
    lateinit var tv_weatherStatus_InFavouriteCityWeatherFragment: TextView
    lateinit var tv_degreeOfTemprature_InFavouriteCityWeatherFragment: TextView
    lateinit var img_weatherStatus_InFavouriteCityWeatherFragment: ImageView

    lateinit var tv_pressure_InFavouriteCityWeatherFragment:TextView
    lateinit var tv_humidity_InFavouriteCityWeatherFragment:TextView
    lateinit var tv_wind_InFavouriteCityWeatherFragment:TextView
    lateinit var tv_cloud_InFavouriteCityWeatherFragment:TextView
    lateinit var tv_visibiliy_InFavouriteCityWeatherFragment:TextView
    private var context_InFavouriteCityWeatherFragment: Context? = null




    override fun onAttach(context: Context) {
        super.onAttach(context)
        context_InFavouriteCityWeatherFragment = context
    }

    override fun onDetach() {
        super.onDetach()
        context_InFavouriteCityWeatherFragment = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.activity_favourite_city_weather, container, false)
        var lat_Egypt = "30.033333"
        var lon_Egypt = "31.233334"

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tv_date_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_Date_city)
        tv_country_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_Country_city)
        tv_weatherStatus_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_weatherState_city)
        tv_degreeOfTemprature_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_degreeOfTemprature_city)
        img_weatherStatus_InFavouriteCityWeatherFragment = view.findViewById(R.id.img_weathertatus_city)

        tv_pressure_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_pressure_value_city)
        tv_humidity_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_humidity_value_city)
        tv_wind_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_wind_value_city)
        tv_cloud_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_cloud_value_city)
        tv_visibiliy_InFavouriteCityWeatherFragment = view.findViewById(R.id.tv_visibility_value_city)


        currentWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance() ,
                WeatherLocalDataSourceImplementation(requireContext())
            )

        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InFavouriteCityWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InFavouriteCityWeatherFragment(view)
        setUpRecyclerView_Hour_InFavouriteCityWeatherFragment()
        setUpRecyclerView_Day_InFavouriteCityWeatherFragment()


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            adapter_Instance_Hour_InFavouriteCityWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Hour(forecastModel.modelWeatherArrayList)
            adapter_Instance_Hour_InFavouriteCityWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            adapter_Instance_Day_InFavouriteCityWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Day(forecastModel.modelWeatherArrayList)
            adapter_Instance_Day_InFavouriteCityWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->


            var dateAndTimeFromWeatherArrayList = forecastModel.modelWeatherArrayList.get(0).dtTxt?.split(" ")

            Log.i("TAG", "onCreateView: weatherStatus: "+ forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description)


            var dtTxt_value = forecastModel.modelWeatherArrayList.get(0).dtTxt
            Log.i("TAG", "onCreateView: Current Weather Fragment: dtTxtValue : " + dtTxt_value)
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(dtTxt_value , firstApiFormat)

            tv_date_InFavouriteCityWeatherFragment.setText(date.dayOfWeek.toString() + System.getProperty("line.separator") +"${dateAndTimeFromWeatherArrayList?.get(0)}")

            tv_weatherStatus_InFavouriteCityWeatherFragment.setText(forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description)

            var tempratureFehrenheit = forecastModel.modelWeatherArrayList.get(0).modelMain?.feelsLike
            var tempratureCelsius = tempratureFehrenheit?.minus(273.15)
            val tempFormated = String.format("%.2f", tempratureCelsius)
            tv_degreeOfTemprature_InFavouriteCityWeatherFragment.setText(tempFormated+"°C")

            var imageIconCode = forecastModel.modelWeatherArrayList.get(0).modelWeather.get(0).icon
            var imageIcon = "https://openweathermap.org/img/wn/$imageIconCode@2x.png"

            var weatherDescription = forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description
            Glide.with(requireContext())
                .load(imageIcon)
                .into(img_weatherStatus_InFavouriteCityWeatherFragment)


            var cityName = forecastModel.modelCity?.name
            var countryCode = forecastModel.modelCity?.country
            var countryName = Locale("", countryCode).displayCountry
            tv_country_InFavouriteCityWeatherFragment.setText(cityName + " , " + countryName)

            var pressure = forecastModel.modelWeatherArrayList.get(0).modelMain?.pressure
            var humidity = forecastModel.modelWeatherArrayList.get(0).modelMain?.humidity
            var wind = forecastModel.modelWeatherArrayList.get(0).modelWind?.speed
            var clouds = forecastModel.modelWeatherArrayList.get(0).modelClouds?.all
            var visibility = forecastModel.modelWeatherArrayList.get(0).visibility

            tv_pressure_InFavouriteCityWeatherFragment.setText(pressure.toString() +" hpa")
            tv_humidity_InFavouriteCityWeatherFragment.setText(humidity.toString()+ " %")
            tv_wind_InFavouriteCityWeatherFragment.setText(wind.toString()+ " m/s")
            tv_cloud_InFavouriteCityWeatherFragment.setText(clouds.toString()+ " %")
            tv_visibiliy_InFavouriteCityWeatherFragment.setText(visibility.toString()+ " m")


        }

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(location.latitude.toString(),location.longitude.toString(),Utils.API_KEY)



    }

    private fun initUI_InFavouriteCityWeatherFragment(view: View){
        recyclerView_Instance_Hour_InFavouriteCityWeatherFragment = view.findViewById(R.id.rv_hours_city)
        recyclerView_Instance_Day_InFavouriteCityWeatherFragment = view.findViewById(R.id.rv_days_city)
    }

    private fun setUpRecyclerView_Hour_InFavouriteCityWeatherFragment(){
        layoutManager_Instance_Hour_InFavouriteCityWeatherFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_Hour_InFavouriteCityWeatherFragment.orientation = RecyclerView.HORIZONTAL
        adapter_Instance_Hour_InFavouriteCityWeatherFragment = CurrentWeatherAdapter_Hour(requireContext(), ArrayList())
        recyclerView_Instance_Hour_InFavouriteCityWeatherFragment.adapter = adapter_Instance_Hour_InFavouriteCityWeatherFragment
        recyclerView_Instance_Hour_InFavouriteCityWeatherFragment.layoutManager = layoutManager_Instance_Hour_InFavouriteCityWeatherFragment
    }


    private fun setUpRecyclerView_Day_InFavouriteCityWeatherFragment(){
        layoutManager_Instance_Day_InFavouriteCityWeatherFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_Day_InFavouriteCityWeatherFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_Day_InFavouriteCityWeatherFragment = CurrentWeatherAdapter_Day(requireContext(), ArrayList())
        recyclerView_Instance_Day_InFavouriteCityWeatherFragment.adapter = adapter_Instance_Day_InFavouriteCityWeatherFragment
        recyclerView_Instance_Day_InFavouriteCityWeatherFragment.layoutManager = layoutManager_Instance_Day_InFavouriteCityWeatherFragment
    }



}

*/