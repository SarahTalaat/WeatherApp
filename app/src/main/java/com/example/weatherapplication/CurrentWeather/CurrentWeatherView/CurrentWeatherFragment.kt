package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModel
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModelFactory_RDS
import com.example.productsmvvm.CurrentWeather.CurrentWeatherView.CurrentWeatherAdapter_Hour
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_WeatherArrayList
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


const val REQUEST_LOCATION_CODE = 2005

class CurrentWeatherFragment : Fragment() {

    private lateinit var currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment: CurrentWeatherViewModelFactory_RDS
    private lateinit var currentWeatherViewModel_Instance_InCurrentWeatherFragmet: CurrentWeatherViewModel
    private lateinit var recyclerView_Instance_Hour_InCurrentWeatherFragment: RecyclerView
    private lateinit var adapter_Instance_Hour_InCurrentWeatherFragment: CurrentWeatherAdapter_Hour
    private lateinit var layoutManager_Instance_Hour_InCurrentWeatherFragment: LinearLayoutManager

    private lateinit var recyclerView_Instance_Day_InCurrentWeatherFragment: RecyclerView
    private lateinit var adapter_Instance_Day_InCurrentWeatherFragment: CurrentWeatherAdapter_Day
    private lateinit var layoutManager_Instance_Day_InCurrentWeatherFragment: LinearLayoutManager

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var data: Uri

    lateinit var tv_date_InCurrentWeatherFagment:TextView
    lateinit var tv_country_InCurrentWeatherFagment: TextView
    lateinit var tv_weatherStatus_InCurrentWeatherFagment: TextView
    lateinit var tv_degreeOfTemprature_InCurrentWeatherFagment: TextView
    lateinit var img_weatherStatus_InCurrentWeatherFagment: ImageView

    lateinit var tv_pressure_InCurrentWeatherFagment:TextView
    lateinit var tv_humidity_InCurrentWeatherFagment:TextView
    lateinit var tv_wind_InCurrentWeatherFagment:TextView
    lateinit var tv_cloud_InCurrentWeatherFagment:TextView
    lateinit var tv_visibiliy_InCurrentWeatherFagment:TextView
    private var context_InCurrentWeatherFragment: Context? = null
    lateinit var progressBar: ProgressBar



    override fun onAttach(context: Context) {
        super.onAttach(context)
        context_InCurrentWeatherFragment = context
    }

    override fun onDetach() {
        super.onDetach()
        context_InCurrentWeatherFragment = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        var lat_Egypt = "30.033333"
        var lon_Egypt = "31.233334"

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tv_date_InCurrentWeatherFagment = view.findViewById(R.id.tv_Date)
        tv_country_InCurrentWeatherFagment = view.findViewById(R.id.tv_Country)
        tv_weatherStatus_InCurrentWeatherFagment = view.findViewById(R.id.tv_weatherState)
        tv_degreeOfTemprature_InCurrentWeatherFagment = view.findViewById(R.id.tv_degreeOfTemprature)
        img_weatherStatus_InCurrentWeatherFagment = view.findViewById(R.id.img_weathertatus)

        tv_pressure_InCurrentWeatherFagment = view.findViewById(R.id.tv_pressure_value)
        tv_humidity_InCurrentWeatherFagment = view.findViewById(R.id.tv_humidity_value)
        tv_wind_InCurrentWeatherFagment = view.findViewById(R.id.tv_wind_value)
        tv_cloud_InCurrentWeatherFagment = view.findViewById(R.id.tv_cloud_value)
        tv_visibiliy_InCurrentWeatherFagment = view.findViewById(R.id.tv_visibility_value)
        progressBar= view.findViewById(R.id.progressBar_currentCity)

        currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getWeatherRemoteDataSourceImplementation_Instance() ,
                WeatherLocalDataSourceImplementation(requireContext())
            )

        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InCurrentWeatherFragment(view)
        setUpRecyclerView_Hour_InCurrentWeatherFragment()
        setUpRecyclerView_Day_InCurrentWeatherFragment()

        lifecycleScope.launch {
            currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastStateFlow_InCurrentWeatherViewModel.collectLatest { result ->
                when(result){
                    is ApiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView_Instance_Day_InCurrentWeatherFragment.visibility = View.GONE
                        recyclerView_Instance_Hour_InCurrentWeatherFragment.visibility =View.GONE
                    }
                    is ApiState.Success_ModelTime_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: Current Weather Fragment : ApiState.Success_ModelTime_Local_InApiState")
                    }
                    is ApiState.Success_ModelFavouriteCity_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: Current Weather Fragment :ApiState.Success_ModelFavouriteCity_Local_InApiState")
                    }
                    is ApiState.Success_ModelForecast_Remote_InApiState -> {
                        progressBar.visibility = View.GONE
                        recyclerView_Instance_Day_InCurrentWeatherFragment.visibility = View.VISIBLE
                        recyclerView_Instance_Hour_InCurrentWeatherFragment.visibility =View.VISIBLE

                        val weatherArrayList: ArrayList<Model_WeatherArrayList>? = result.data?.modelWeatherArrayList
                        if(weatherArrayList!=null){
                            adapter_Instance_Day_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Day(weatherArrayList)
                            adapter_Instance_Hour_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Hour(weatherArrayList)
                            adapter_Instance_Day_InCurrentWeatherFragment.notifyDataSetChanged()
                            adapter_Instance_Hour_InCurrentWeatherFragment.notifyDataSetChanged()

                        }

                        var dateAndTimeFromWeatherArrayList = result.data?.modelWeatherArrayList?.get(0)?.dtTxt?.split(" ")

                        Log.i("TAG", "onCreateView: weatherStatus: "+ result.data?.modelWeatherArrayList?.get(2)?.modelWeather?.get(0)?.description)


                        var dtTxt_value = result.data?.modelWeatherArrayList?.get(0)?.dtTxt
                        Log.i("TAG", "onCreateView: Current Weather Fragment: dtTxtValue : " + dtTxt_value)
                        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val date = LocalDate.parse(dtTxt_value , firstApiFormat)

                        tv_date_InCurrentWeatherFagment.setText(date.dayOfWeek.toString() + System.getProperty("line.separator") +"${dateAndTimeFromWeatherArrayList?.get(0)}")

                        tv_weatherStatus_InCurrentWeatherFagment.setText(result.data?.modelWeatherArrayList?.get(2)?.modelWeather?.get(0)?.description)

                        var temprature = result.data?.modelWeatherArrayList?.get(0)?.modelMain?.feelsLike
                       /*
                        var tempratureCelsius = temprature?.minus(273.15)
                        val tempFormated = String.format("%.2f", tempratureCelsius)
                        tv_degreeOfTemprature_InCurrentWeatherFagment.setText(tempFormated+"°C")
                        */

                        val sharedPreferencesName = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                        var sp_unit_value =sharedPreferencesName?.getString(Utils.TEMPRATURE_KEY,null)



                            if(sp_unit_value == Utils.CELSIUS){
                                tv_degreeOfTemprature_InCurrentWeatherFagment.setText("$temprature°C")
                            }else if(sp_unit_value == Utils.FAHRENHEIT){
                                tv_degreeOfTemprature_InCurrentWeatherFagment.setText("$temprature°F")
                            }else{
                                tv_degreeOfTemprature_InCurrentWeatherFagment.setText("$temprature°K")
                            }


                        var imageIconCode = result.data?.modelWeatherArrayList?.get(0)?.modelWeather?.get(0)?.icon
                        var imageIcon = "https://openweathermap.org/img/wn/$imageIconCode@2x.png"

                        var weatherDescription = result.data?.modelWeatherArrayList?.get(2)?.modelWeather?.get(0)?.description
                        Glide.with(requireContext())
                            .load(imageIcon)
                            .into(img_weatherStatus_InCurrentWeatherFagment)


                        var cityName = result.data?.modelCity?.name
                        var countryCode = result.data?.modelCity?.country
                        var countryName = Locale("", countryCode).displayCountry
                        tv_country_InCurrentWeatherFagment.setText(cityName + " , " + countryName)

                        var pressure = result.data?.modelWeatherArrayList?.get(0)?.modelMain?.pressure
                        var humidity = result.data?.modelWeatherArrayList?.get(0)?.modelMain?.humidity
                        var wind = result.data?.modelWeatherArrayList?.get(0)?.modelWind?.speed
                        var clouds = result.data?.modelWeatherArrayList?.get(0)?.modelClouds?.all
                        var visibility = result.data?.modelWeatherArrayList?.get(0)?.visibility

                        tv_pressure_InCurrentWeatherFagment.setText(pressure.toString() +" hpa")
                        tv_humidity_InCurrentWeatherFagment.setText(humidity.toString()+ " %")

                    //    var sp_windSpeed_value = SharedPrefrences.getInstance(requireContext()).getWindSpeedValue(Utils.WINDSPEED_KEY)


                        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                        var sp_windSpeed_value =sharedPreferences?.getString(Utils.WINDSPEED_KEY,null)

                        Log.i("WindSpeed", "onViewCreated:sp_windspeed_value: $sp_windSpeed_value ")

                        if (sp_windSpeed_value == Utils.MILE_HOUR){
                            if(wind != null){
                                Log.i("WindSpeed", "onViewCreated: wind :$wind")

                                var windSpeed_milePerHour = convertMeterPerSec_To_MilePerHour(wind!!)
                                Log.i("WindSpeed", "onViewCreated: windspeedmile per hour :$windSpeed_milePerHour")
                                tv_wind_InCurrentWeatherFagment.setText(windSpeed_milePerHour.toString()+ " mile/hr")
                            }
                        }else{
                            tv_wind_InCurrentWeatherFagment.setText(wind.toString()+ " meter/sec")
                        }

                        tv_cloud_InCurrentWeatherFagment.setText(clouds.toString()+ " %")
                        tv_visibiliy_InCurrentWeatherFagment.setText(visibility.toString()+ " m")

                    }
                    is ApiState.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context,"There is problem in the server", Toast.LENGTH_LONG).show()
                    }
                    is ApiState.Success_ModelAlert_Remote_InApiState ->{
                        Log.i("TAG", "onCreate: FavouriteCityWeatherFragment ")
                    }
                }

            }

        }

        getFreshLocation()
        //mapPermissions_AndGettingCurrentLocation_AndDisplayDataInFragmentAndAdapters_InCurrentWeatherFragment()

    }

    private fun initUI_InCurrentWeatherFragment(view: View){
        recyclerView_Instance_Hour_InCurrentWeatherFragment = view.findViewById(R.id.rv_hours)
        recyclerView_Instance_Day_InCurrentWeatherFragment = view.findViewById(R.id.rv_days_city)
    }

    private fun setUpRecyclerView_Hour_InCurrentWeatherFragment(){
        layoutManager_Instance_Hour_InCurrentWeatherFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_Hour_InCurrentWeatherFragment.orientation = RecyclerView.HORIZONTAL
        adapter_Instance_Hour_InCurrentWeatherFragment = CurrentWeatherAdapter_Hour(requireContext(), ArrayList())
        recyclerView_Instance_Hour_InCurrentWeatherFragment.adapter = adapter_Instance_Hour_InCurrentWeatherFragment
        recyclerView_Instance_Hour_InCurrentWeatherFragment.layoutManager = layoutManager_Instance_Hour_InCurrentWeatherFragment
    }


    private fun setUpRecyclerView_Day_InCurrentWeatherFragment(){
        layoutManager_Instance_Day_InCurrentWeatherFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_Day_InCurrentWeatherFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_Day_InCurrentWeatherFragment = CurrentWeatherAdapter_Day(requireContext(), ArrayList())
        recyclerView_Instance_Day_InCurrentWeatherFragment.adapter = adapter_Instance_Day_InCurrentWeatherFragment
        recyclerView_Instance_Day_InCurrentWeatherFragment.layoutManager = layoutManager_Instance_Day_InCurrentWeatherFragment
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if(checkPermissions()){
            if(isLocationEnabled()){
                getFreshLocation()
            }else{
                enableLocationServices()
            }
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION

                ),
                REQUEST_LOCATION_CODE
            )
        }
    }

    fun convertMeterPerSec_To_MilePerHour(meterPerSec: Double):Double{
        return  meterPerSec*2.23694
    }

    fun checkPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean{
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getFreshLocation(){

        var currentLocationInGetFreshLocation:String=""
        Log.i("TAG", "getFreshLocation() " )

        //Entry point
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        //Location request to determine min distance , min time , accuracy level to get location updates
        Log.i("TAG", "after entry point " )
        fusedLocationProviderClient.requestLocationUpdates(
            com.google.android.gms.location.LocationRequest.Builder(2000).apply {
                setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                Log.i("TAG", "first parameter " )
            }.build(),
            //Call back to get the location
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    Log.i("TAG", "super.onLocationResult(p0) " )
                    val location = p0.lastLocation
                    Log.i("TAG", "latitude: " + location?.latitude.toString())
                    Log.i("TAG", "longitue: " + location?.longitude.toString())
                   // tvLongitude.text = location?.longitude.toString()
                   // tvLatitude.text = location?.latitude.toString()

                    if(location?.latitude!= null && location?.longitude!= null){
                        val context_InGetFreshLocation = context_InCurrentWeatherFragment ?: return
                        val geoUtils = GeoUtils(context_InGetFreshLocation)
                        val address = geoUtils.getAddress(location?.latitude, location?.longitude)
                        Log.i("TAG", "onLocationResult: full addess: "+address)


                       // currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getList_FromRetrofit_InCurrentWeatherViewModel(location.latitude.toString(),location.longitude.toString(),Utils.API_KEY)
                       // currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(location.latitude.toString(),location.longitude.toString(),Utils.API_KEY)

                        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                        var sp_location_value =sharedPreferences?.getString(Utils.LOCATION_KEY,null)

                        if(sp_location_value == Utils.MAP ){

                            val sharedPreferences = context?.getSharedPreferences(Utils.SETTINGS_MAP_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                            var sp_lat_value =sharedPreferences?.getString(Utils.Settings_MAP_SP_LAT_KEY,null)
                            var sp_lon_value =sharedPreferences?.getString(Utils.Settings_MAP_SP_LON_KEY,null)

                            if(sp_lat_value != null && sp_lon_value != null){
                                currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(sp_lat_value,sp_lon_value,getUnit(),getLang(),Utils.API_KEY)
                            }

                        }else{
                            currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(location.latitude.toString(),location.longitude.toString(),getUnit(),getLang(),Utils.API_KEY)
                        }



                        //     tvTextLocation.text = address
                    }

                    //      fusedLocationProviderClient.removeLocationUpdates(this)
                }
            },
            Looper.myLooper()
        ) // end of request location updates

    }


    fun enableLocationServices(){
        Toast.makeText(requireContext(),"Turn on Location", Toast.LENGTH_SHORT).show()

        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("TAG", "request code: " + requestCode)
        if(requestCode == REQUEST_LOCATION_CODE){
            if(grantResults.size > 1 && grantResults.get(0) == PackageManager.PERMISSION_GRANTED){
                getFreshLocation()
            }
        }
    }
    fun convertTemperature(
        temperature: Double,
        currentUnit: TemperatureUnit,
        targetUnit: TemperatureUnit
    ): Double {
        return when (currentUnit) {
            targetUnit -> temperature // No conversion needed
            TemperatureUnit.CELSIUS -> {
                when (targetUnit) {
                    TemperatureUnit.KELVIN -> temperature + 273.15
                    TemperatureUnit.FAHRENHEIT -> temperature * 9 / 5 + 32
                    else -> temperature
                }
            }
            TemperatureUnit.KELVIN -> {
                when (targetUnit) {
                    TemperatureUnit.CELSIUS -> temperature - 273.15
                    TemperatureUnit.FAHRENHEIT -> temperature * 9 / 5 - 459.67
                    else -> temperature
                }
            }
            TemperatureUnit.FAHRENHEIT -> {
                when (targetUnit) {
                    TemperatureUnit.CELSIUS -> (temperature - 32) * 5 / 9
                    TemperatureUnit.KELVIN -> (temperature + 459.67) * 5 / 9
                    else -> temperature
                }
            }
        }
    }

    fun temprature(){
        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        var sp_windSpeed_value =sharedPreferences?.getString(Utils.TEMPRATURE_KEY,null)

        if(sp_windSpeed_value == null){

             if(sp_windSpeed_value == Utils.CELSIUS){

            }else if(sp_windSpeed_value == Utils.FAHRENHEIT){

            }else{

            }
        }

    }

    enum class TemperatureUnit {
        CELSIUS,
        KELVIN,
        FAHRENHEIT
    }

    /*
        CELSIUS
        FAHRENHEIT
     */


    fun getUnit(): String{
        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        var sp_unit_value =sharedPreferences?.getString(Utils.TEMPRATURE_KEY,null)

        if (sp_unit_value != null){

            if(sp_unit_value == Utils.CELSIUS){
                return "metric"
            }else if(sp_unit_value == Utils.FAHRENHEIT) {
                return "imperial"
            }
        }

        return ""

    }

    fun getLang(): String{
        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        var sp_lang_value =sharedPreferences?.getString(Utils.LANGUAGE_KEY,null)

        if(sp_lang_value != null){
            if(sp_lang_value == Utils.ARABIC){
                return "ar"
            }
        }
        return "en"
    }


}