package com.example.weatherapplication.FavouriteWeather.FavouriteWeatherView

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel.FavouriteCityViewModel
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.FavouriteProductsAdapter
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.OnFavouriteCityClickListenerInterface
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteCityViewModelFactory_LDS
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Map.MapActivity
import com.example.weatherapplication.Model.Model_FavouriteCity
import com.example.weatherapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavouriteCityFragment : Fragment(), OnFavouriteCityClickListenerInterface {


    lateinit var floatingActionButton_map: FloatingActionButton


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

        floatingActionButton_map.setOnClickListener(){
            var intent = Intent(context , MapActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        favouriteCityViewModelFactory_Instance_LDS_InFavouriteCityFragment = FavouriteCityViewModelFactory_LDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
            WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance(),
            WeatherLocalDataSourceImplementation(requireContext())
        ))

        favouriteCityViewModel_Instance_InFavouriteCityActivity = ViewModelProvider(this,favouriteCityViewModelFactory_Instance_LDS_InFavouriteCityFragment).get(FavouriteCityViewModel::class.java)

        initUI_InFavouriteProductsActivity(view)
        setUpRecyclerView_InFavouriteProductsActivity()

        favouriteCityViewModel_Instance_InFavouriteCityActivity.favouriteCityLiveDataList_InFavouriteCityViewModel .observe(viewLifecycleOwner){
                products ->
            adapter_Instance_InFavouriteCityFragment.setFavouriteCityList_InFavouriteCityAdapter(products as ArrayList<Model_FavouriteCity>)
            adapter_Instance_InFavouriteCityFragment.notifyDataSetChanged()
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

}