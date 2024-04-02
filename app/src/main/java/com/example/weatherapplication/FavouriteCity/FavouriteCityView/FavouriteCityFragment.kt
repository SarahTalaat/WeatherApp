package com.example.weatherapplication.FavouriteCity.FavouriteCityView

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel.FavouriteCityViewModel
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.FavouriteProductsAdapter
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.OnFavouriteCityClickListenerInterface
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteCityViewModelFactory_LDS
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherView.FavouriteCityWeatherActivity
import com.example.weatherapplication.Map.MapView.MapActivity
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState
import com.example.weatherapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavouriteCityFragment : Fragment(), OnFavouriteCityClickListenerInterface {


    lateinit var floatingActionButton_map: FloatingActionButton
    lateinit var progressBar: ProgressBar

    private lateinit var favouriteCityViewModelFactory_Instance_LDS_InFavouriteCityFragment: FavouriteCityViewModelFactory_LDS
    private lateinit var favouriteCityViewModel_Instance_InFavouriteCityActivity: FavouriteCityViewModel
    private lateinit var recyclerView_Instance_InFavouriteCityActivity: RecyclerView
    private lateinit var adapter_Instance_InFavouriteCityFragment: FavouriteProductsAdapter
    private lateinit var layoutManager_Instance_InFavouriteCityFragment: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_favourite, container, false)

        floatingActionButton_map = view.findViewById(R.id.floatingActionButton_map)
        progressBar= view.findViewById(R.id.progressBar_favouriteCity)
        floatingActionButton_map.setOnClickListener(){
            var intent = Intent(context , MapActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val latitudeFromMapBundle_Value = arguments?.getString(Utils.FAVOURITE_CITY_LATITUDE)
        val longitudeFromMapBundle_Value = arguments?.getString(Utils.FAVOURITE_CITY_LONGITUDE)

        val bundle = Bundle()
        bundle.putString(Utils.FAVOURITE_CITY_LATITUDE, latitudeFromMapBundle_Value )
        bundle.putString(Utils.FAVOURITE_CITY_LONGITUDE,longitudeFromMapBundle_Value)
        val intent = Intent(activity, FavouriteCityWeatherActivity::class.java)
        intent.putExtras(bundle)


        favouriteCityViewModelFactory_Instance_LDS_InFavouriteCityFragment = FavouriteCityViewModelFactory_LDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
            WeatherRemoteDataSourceImplementation.getWeatherRemoteDataSourceImplementation_Instance(),
            WeatherLocalDataSourceImplementation(requireContext())
        ))

        favouriteCityViewModel_Instance_InFavouriteCityActivity = ViewModelProvider(this,favouriteCityViewModelFactory_Instance_LDS_InFavouriteCityFragment).get(FavouriteCityViewModel::class.java)

        initUI_InFavouriteProductsActivity(view)
        setUpRecyclerView_InFavouriteProductsActivity()


        lifecycleScope.launch {
            favouriteCityViewModel_Instance_InFavouriteCityActivity.favouriteCityStateFlowList_InFavouriteCityViewModel.collectLatest { result ->
                when(result){
                    is ApiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView_Instance_InFavouriteCityActivity.visibility = View.GONE
                    }
                    is ApiState.Success_ModelTime_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: favourite city fragment: ApiState.Success_ModelTime_Local_InApiState ")
                    }
                    is ApiState.Success_ModelForecast_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: favourite city fragment: ApiState.Success_ModelForecast_Local_InApiState ")

                    }
                    is ApiState.Success_ModelFavouriteCity_Local_InApiState -> {
                        progressBar.visibility = View.GONE
                        recyclerView_Instance_InFavouriteCityActivity.visibility = View.VISIBLE

                        if(result.data != null){
                            adapter_Instance_InFavouriteCityFragment.setFavouriteCityList_InFavouriteCityAdapter(result.data as ArrayList<Model_FavouriteCity>)
                            adapter_Instance_InFavouriteCityFragment.notifyDataSetChanged()

                        }
                    }
                    is ApiState.Success_ModelForecast_Remote_InApiState -> {
                        Log.i("TAG", "onViewCreated: favourite city fragment : ApiState.Success_ModelForecast_Remote_InApiState")
                    }
                    is ApiState.Success_ModelAlert_Remote_InApiState -> {
                        Log.i("TAG", "onViewCreated: favourite city fragment : ApiState.Success_ModelAlert_Remote_InApiState ")
                    }
                    is ApiState.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context,"There is problem in fetching data from database", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


        favouriteCityViewModel_Instance_InFavouriteCityActivity.getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()

    }


    private fun initUI_InFavouriteProductsActivity(view:View){
        recyclerView_Instance_InFavouriteCityActivity = view.findViewById(R.id.rv_favourite)
    }

    private fun setUpRecyclerView_InFavouriteProductsActivity(){
        layoutManager_Instance_InFavouriteCityFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_InFavouriteCityFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_InFavouriteCityFragment = FavouriteProductsAdapter(requireContext(), ArrayList(),this)
        recyclerView_Instance_InFavouriteCityActivity.adapter = adapter_Instance_InFavouriteCityFragment
        recyclerView_Instance_InFavouriteCityActivity.layoutManager = layoutManager_Instance_InFavouriteCityFragment
    }

    override fun onClick_DeleteFavouriteCityFromFavouriteCityFragment_InOnFavouriteClickListenerInterface(
        city: Model_FavouriteCity
    ) {
        favouriteCityViewModel_Instance_InFavouriteCityActivity.deleteFavouriteCity_InFavouriteCityViewModel(city)

    }

    override fun onClick_NavigateToFavouriteCityWeatherActivity_InOnFavouriteClickListenerInterface(
        latitude: String?,
        longitude: String?
    ) {
        val bundle = Bundle()
        bundle.putString(Utils.FAVOURITE_CITY_LATITUDE, latitude)
        bundle.putString(Utils.FAVOURITE_CITY_LONGITUDE, longitude)
        val intent = Intent(requireContext(), FavouriteCityWeatherActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

    }

}