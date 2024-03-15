package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModel
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModelFactory_RDS
import com.example.productsmvvm.CurrentWeather.CurrentWeatherView.CurrentWeatherAdapter
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model_WeatherArrayList
import com.example.weatherapplication.R

class CurrentWeatherFragment : Fragment() {

    private lateinit var currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment: CurrentWeatherViewModelFactory_RDS
    private lateinit var currentWeatherViewModel_Instance_InCurrentWeatherFragmet: CurrentWeatherViewModel
    private lateinit var recyclerView_Instance_InCurrentWeatherFragment: RecyclerView
    private lateinit var adapter_Instance_InCurrentWeatherFragment: CurrentWeatherAdapter
    private lateinit var layoutManager_Instance_InCurrentWeatherFragment: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_home_2, container, false)
        var lat_Egypt = "30.033333"
        var lon_Egypt = "31.233334"

        var tv_date_InCurrentWeatherFagment:TextView = view.findViewById(R.id.tv_Date)
        var tv_country_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_Country)
        var tv_weatherStatus_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_weatherState)
        var tv_degreeOfTemprature_InCurrentWeatherFagment: TextView = view.findViewById(R.id.tv_degreeOfTemprature)

        currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance()
            )
        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InAllProductsActivity(view)
        setUpRecyclerView_InCurrentWeatherActivity()


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.weatherArrayListLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                weatherArrayList ->
            adapter_Instance_InCurrentWeatherFragment.settingWeatherArrayList_InCurrentWeatherAdapter(weatherArrayList as ArrayList<Model_WeatherArrayList>)
            adapter_Instance_InCurrentWeatherFragment.notifyDataSetChanged()
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.weatherArrayListLiveDataList_InCurrentWeatherViewModel .observe(viewLifecycleOwner){
                weatherArrayList ->
            var dateAndTimeFromWeatherArrayList = weatherArrayList.get(8).dtTxt?.split(" ")
          /*
            var dateFromDateAndTimeFromWeatherArrayList = dateAndTimeFromWeatherArrayList?.get(0)?.split("-")

            var year = dateFromDateAndTimeFromWeatherArrayList?.get(0)
            var month = dateFromDateAndTimeFromWeatherArrayList?.get(1)
            var day = dateFromDateAndTimeFromWeatherArrayList?.get(2)
           */
            Log.i("TAG", "onCreateView: weatherStatus: "+ weatherArrayList.get(2).modelWeather.get(0).description)
            tv_date_InCurrentWeatherFagment.setText(dateAndTimeFromWeatherArrayList?.get(0))
            tv_weatherStatus_InCurrentWeatherFagment.setText(weatherArrayList.get(2).modelWeather.get(0).description)

          //  var tempratureFehrenheit = weatherArrayList.get()

        }

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.cityLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
            city ->
            var cityObject = city
            var city_name = city.name
            var city_country = city.country
            var city_coord_lat = city.modelCoord?.lat
            var city_coord_lon = city.modelCoord?.lon

            Log.i("TAG", "onCreateView: country name: "+city_country)
            tv_country_InCurrentWeatherFagment.setText(city_country)
        }


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getList_FromRetrofit_InCurrentWeatherViewModel(lat_Egypt,lon_Egypt,Utils.API_KEY)
        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getCity_FromRetrofit_InCurrentWeatherViewModel("Boulder Creek",Utils.API_KEY)// ‘Al Atabah

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
        adapter_Instance_InCurrentWeatherFragment = CurrentWeatherAdapter(requireContext(), ArrayList())
        recyclerView_Instance_InCurrentWeatherFragment.adapter = adapter_Instance_InCurrentWeatherFragment
        recyclerView_Instance_InCurrentWeatherFragment.layoutManager = layoutManager_Instance_InCurrentWeatherFragment
    }

}