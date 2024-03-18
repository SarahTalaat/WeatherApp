package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.core.content.ContextCompat
import com.example.weatherapplication.MapActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
    lateinit var floatingActionButton: FloatingActionButton


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
        floatingActionButton = view.findViewById(R.id.floatingActionButton_map)



        currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance()
            )
        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InCurrentWeatherFragment(view)
        setUpRecyclerView_Hour_InCurrentWeatherFragment()
        setUpRecyclerView_Day_InCurrentWeatherFragment()

        mapPermissions_AndGettingCurrentLocation_AndDisplayDataInFragmentAndAdapters_InCurrentWeatherFragment()

        floatingActionButton.setOnClickListener(){
            var intent = Intent(context, MapActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initUI_InCurrentWeatherFragment(view: View){
        recyclerView_Instance_Hour_InCurrentWeatherFragment = view.findViewById(R.id.rv_hours)
        recyclerView_Instance_Day_InCurrentWeatherFragment = view.findViewById(R.id.rv_days)
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
    fun mapPermissions_AndGettingCurrentLocation_AndDisplayDataInFragmentAndAdapters_InCurrentWeatherFragment(){

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
                        val geoUtils = GeoUtils(requireContext())
                        val address = geoUtils.getAddress(location?.latitude, location?.longitude)
                        Log.i("TAG", "onLocationResult: full addess: "+address)

                        getLatAndLonFromGetFreshLocationFunctionToDisplayDataInFragmentAndAdapters_InCurrentWeatherFragment(location.latitude.toString(),location.longitude.toString())

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

    fun getLatAndLonFromGetFreshLocationFunctionToDisplayDataInFragmentAndAdapters_InCurrentWeatherFragment(lat_constructorParameter: String, lon_constructorParameter:String){




        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            adapter_Instance_Hour_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Hour(forecastModel.modelWeatherArrayList)
            adapter_Instance_Hour_InCurrentWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            adapter_Instance_Day_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter_Day(forecastModel.modelWeatherArrayList)
            adapter_Instance_Day_InCurrentWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->


            var dateAndTimeFromWeatherArrayList = forecastModel.modelWeatherArrayList.get(0).dtTxt?.split(" ")

            Log.i("TAG", "onCreateView: weatherStatus: "+ forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description)


            var dtTxt_value = forecastModel.modelWeatherArrayList.get(0).dtTxt
            Log.i("TAG", "onCreateView: Current Weather Fragment: dtTxtValue : " + dtTxt_value)
            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(dtTxt_value , firstApiFormat)

            tv_date_InCurrentWeatherFagment.setText(date.dayOfWeek.toString() + System.getProperty("line.separator") +"${dateAndTimeFromWeatherArrayList?.get(0)}")

            tv_weatherStatus_InCurrentWeatherFagment.setText(forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description)

            var tempratureFehrenheit = forecastModel.modelWeatherArrayList.get(0).modelMain?.feelsLike
            var tempratureCelsius = tempratureFehrenheit?.minus(273.15)
            val tempFormated = String.format("%.2f", tempratureCelsius)
            tv_degreeOfTemprature_InCurrentWeatherFagment.setText(tempFormated+"°C")

            var imageIconCode = forecastModel.modelWeatherArrayList.get(0).modelWeather.get(0).icon
            var imageIcon = "https://openweathermap.org/img/wn/$imageIconCode@2x.png"

            var weatherDescription = forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description
            Glide.with(requireContext())
                .load(imageIcon)
                .into(img_weatherStatus_InCurrentWeatherFagment)


            var cityName = forecastModel.modelCity?.name
            var countryCode = forecastModel.modelCity?.country
            var countryName = Locale("", countryCode).displayCountry
            tv_country_InCurrentWeatherFagment.setText(cityName + " , " + countryName)

            var pressure = forecastModel.modelWeatherArrayList.get(0).modelMain?.pressure
            var humidity = forecastModel.modelWeatherArrayList.get(0).modelMain?.humidity
            var wind = forecastModel.modelWeatherArrayList.get(0).modelWind?.speed
            var clouds = forecastModel.modelWeatherArrayList.get(0).modelClouds?.all
            var visibility = forecastModel.modelWeatherArrayList.get(0).visibility

            tv_pressure_InCurrentWeatherFagment.setText(pressure.toString() +" hpa")
            tv_humidity_InCurrentWeatherFagment.setText(humidity.toString()+ " %")
            tv_wind_InCurrentWeatherFagment.setText(wind.toString()+ " m/s")
            tv_cloud_InCurrentWeatherFagment.setText(clouds.toString()+ " %")
            tv_visibiliy_InCurrentWeatherFagment.setText(visibility.toString()+ " m")


        }

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getList_FromRetrofit_InCurrentWeatherViewModel(lat_constructorParameter,lon_constructorParameter,Utils.API_KEY)
        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(lat_constructorParameter,lon_constructorParameter,Utils.API_KEY)

    }


}