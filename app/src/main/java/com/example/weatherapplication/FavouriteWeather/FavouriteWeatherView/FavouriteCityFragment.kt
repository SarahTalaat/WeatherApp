package com.example.weatherapplication.FavouriteWeather.FavouriteWeatherView

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapplication.MapActivity
import com.example.weatherapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavouriteCityFragment : Fragment() {


    lateinit var floatingActionButton_map: FloatingActionButton


    private lateinit var favouriteProductsViewModelFactory_Instance_LDS_InFavouriteProductsActivity: FavouriteProductsViewModelFactory_LDS
    private lateinit var favouriteProductsViewModel_Instance_InFavouriteProductsActivity: FavouriteProductsViewModel
    private lateinit var recyclerView_Instance_InFavouriteProductsActivity: RecyclerView
    private lateinit var adapter_Instance_InFavouriteProductsActivity: FavouriteProductsAdapter
    private lateinit var layoutManager_Instance_InFavouriteProductsActivity: LinearLayoutManager

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


        favouriteProductsViewModelFactory_Instance_LDS_InFavouriteProductsActivity = FavouriteProductsViewModelFactory_LDS(WeatherRepositoryImplementation.getProductsRepositoryImplementationInstance(
            WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance(),
            WeatherLocalDataSourceImplementation(this)
        ))

        favouriteProductsViewModel_Instance_InFavouriteProductsActivity = ViewModelProvider(this,favouriteProductsViewModelFactory_Instance_LDS_InFavouriteProductsActivity).get(FavouriteProductsViewModel::class.java)

        initUI_InFavouriteProductsActivity()
        setUpRecyclerView_InFavouriteProductsActivity()

        favouriteProductsViewModel_Instance_InFavouriteProductsActivity.productsLiveDataList_InFavouriteProductsViewModel.observe(this){
                products ->
            adapter_Instance_InFavouriteProductsActivity.setProductList_InFavouriteProductsAdapter(products as ArrayList<Products>)
            adapter_Instance_InFavouriteProductsActivity.notifyDataSetChanged()
        }


        favouriteProductsViewModel_Instance_InFavouriteProductsActivity.getAllLocalProducts_StoredInDatabase_InFavouriteProductsViewModel()

    }


    private fun initUI_InFavouriteProductsActivity(){
        recyclerView_Instance_InFavouriteProductsActivity = findViewById(R.id.rv_Favourite)
    }

    private fun setUpRecyclerView_InFavouriteProductsActivity(){
        layoutManager_Instance_InFavouriteProductsActivity = LinearLayoutManager(this)
        layoutManager_Instance_InFavouriteProductsActivity.orientation = RecyclerView.VERTICAL
        adapter_Instance_InFavouriteProductsActivity = FavouriteProductsAdapter(this, ArrayList(),this)
        recyclerView_Instance_InFavouriteProductsActivity.adapter = adapter_Instance_InFavouriteProductsActivity
        recyclerView_Instance_InFavouriteProductsActivity.layoutManager = layoutManager_Instance_InFavouriteProductsActivity
    }

    override fun onClick_DeleteProductFromFavouriteProductsActivity_InOnFavouriteClickListenerInterface(product: Products) {
        favouriteProductsViewModel_Instance_InFavouriteProductsActivity.deleteProduct_InFavouriteProductsViewModel(product)
    }

}