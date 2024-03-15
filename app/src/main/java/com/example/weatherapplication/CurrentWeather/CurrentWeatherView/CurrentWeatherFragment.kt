package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        var view: View = inflater.inflate(R.layout.fragment_home, container, false)

        currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getProductsRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance()
            )
        )

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet = ViewModelProvider(this,currentWeatherViewModelFactory_Instance_RDS_InCurrentWeatherFragment).get(
            CurrentWeatherViewModel::class.java)

        initUI_InAllProductsActivity(view)
        setUpRecyclerView_InCurrentWeatherActivity()


        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.intLiveDataList_InCurrentWeatherViewModel.observe(viewLifecycleOwner){
                weatherArrayList ->
            adapter_Instance_InCurrentWeatherFragment.setWeatherArrayList_InCurrentWeatherAdapter(weatherArrayList as ArrayList<Model_WeatherArrayList>)
            adapter_Instance_InCurrentWeatherFragment.notifyDataSetChanged()
        }

        var lat_Egypt = "30.033333"
        var lon_Egypt = "31.233334"

        currentWeatherViewModel_Instance_InCurrentWeatherFragmet.getList_FromRetrofit_InCurrentWeatherViewModel(lat_Egypt,lon_Egypt,Utils.API_KEY)


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
        layoutManager_Instance_InCurrentWeatherFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_InCurrentWeatherFragment = CurrentWeatherAdapter(requireContext(), ArrayList())
        recyclerView_Instance_InCurrentWeatherFragment.adapter = adapter_Instance_InCurrentWeatherFragment
        recyclerView_Instance_InCurrentWeatherFragment.layoutManager = layoutManager_Instance_InCurrentWeatherFragment
    }

}