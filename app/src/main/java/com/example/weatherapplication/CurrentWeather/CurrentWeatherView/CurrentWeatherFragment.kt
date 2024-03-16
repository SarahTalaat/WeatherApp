package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

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

class CurrentWeatherFragment : Fragment() {

    private lateinit var currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment: CurrentWeatherViewModelFactory_RDS
    private lateinit var currentWeatherViewModel_Instance_InCurrentWeatherFragmet: CurrentWeatherViewModel
    private lateinit var recyclerView_Instance_InCurrentWeatherFragment: RecyclerView
    private lateinit var adapter_hour_Instance_InCurrentWeatherFragment: CurrentWeatherAdapter_Hour
    private lateinit var layoutManager_Instance_InCurrentWeatherFragment: LinearLayoutManager

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

        var tv_date_InCurrentWeatherFagment:TextView = view.findViewById(R.id.tv_Date)
        var tv_country_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_Country)
        var tv_weatherStatus_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_weatherState)
        var tv_degreeOfTemprature_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_degreeOfTemprature)
        var img_weatherStatus_InCurrentWeatherFagment: ImageView = view.findViewById(R.id.img_weathertatus)


        currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance()
            )
        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InAllProductsActivity(view)
        setUpRecyclerView_InCurrentWeatherActivity()

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            adapter_hour_Instance_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter(forecastModel.modelWeatherArrayList)
            adapter_hour_Instance_InCurrentWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                forecastModel ->
            var dateAndTimeFromWeatherArrayList = forecastModel.modelWeatherArrayList.get(8).dtTxt?.split(" ")

            Log.i("TAG", "onCreateView: weatherStatus: "+ forecastModel.modelWeatherArrayList.get(2).modelWeather.get(0).description)
            tv_date_InCurrentWeatherFagment.setText(dateAndTimeFromWeatherArrayList?.get(0))
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
        }

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.forecastLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
            forecastModel ->

            var countryName = forecastModel.modelCity?.country
            tv_country_InCurrentWeatherFagment.setText(countryName)
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getList_FromRetrofit_InCurrentWeatherViewModel(lat_Egypt,lon_Egypt,Utils.API_KEY)
        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getForecast_FromRetrofit_InCurrentWeatherViewModel(lat_Egypt,lon_Egypt,Utils.API_KEY)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initUI_InAllProductsActivity(view: View){
        recyclerView_Instance_InCurrentWeatherFragment = view.findViewById(R.id.rv_hours)
    }

    private fun setUpRecyclerView_InCurrentWeatherActivity(){
        layoutManager_Instance_InCurrentWeatherFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_InCurrentWeatherFragment.orientation = RecyclerView.HORIZONTAL
        adapter_hour_Instance_InCurrentWeatherFragment = CurrentWeatherAdapter_Hour(requireContext(), ArrayList())
        recyclerView_Instance_InCurrentWeatherFragment.adapter = adapter_hour_Instance_InCurrentWeatherFragment
        recyclerView_Instance_InCurrentWeatherFragment.layoutManager = layoutManager_Instance_InCurrentWeatherFragment
    }

}