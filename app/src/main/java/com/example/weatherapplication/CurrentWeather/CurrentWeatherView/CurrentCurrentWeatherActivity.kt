package com.example.productsmvvm.AllProducts.AllProductsView

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModel
import com.example.productsmvvm.AllProducts.AllProductsViewModel.CurrentWeatherViewModelFactory_RDS
//import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
//import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModel
//import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModelFactory_LDS
//import com.example.productsmvvm.Model.Products

/*
class CurrentCurrentWeatherActivity : AppCompatActivity() , OnCurrentWeatherClickListenerInterface{
    private lateinit var favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity: FavouriteProductsViewModelFactory_LDS
    private lateinit var allProductsViewModelFactory_Instance_RDS_InAllProductsActivity: CurrentWeatherViewModelFactory_RDS
    private lateinit var favouriteProductsViewModel_Instance_InAllProductsActivity: FavouriteProductsViewModel
    private lateinit var allProductsViewModel_Instance_InCrrentWeatherActivity: CurrentWeatherViewModel
    private lateinit var recyclerView_Instance_InAllProductsActivity: RecyclerView
    private lateinit var adapter_Instance_InAllProductsActivity: CurrentWeatherAdapter
    private lateinit var layoutManager_Instance_InAllProductsActivity: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)

        favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity = FavouriteProductsViewModelFactory_LDS(
            WeatherRepositoryImplementation.getProductsRepositoryImplementationInstance(
            WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance(),
            WeatherLocalDataSourceImplementation(this)
            )
        )

        allProductsViewModelFactory_Instance_RDS_InAllProductsActivity = CurrentWeatherViewModelFactory_RDS(
            WeatherRepositoryImplementation.getProductsRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance(),
                WeatherLocalDataSourceImplementation(this)
            )
        )

        favouriteProductsViewModel_Instance_InAllProductsActivity = ViewModelProvider(this,favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity).get(
            FavouriteProductsViewModel::class.java)

        allProductsViewModel_Instance_InCrrentWeatherActivity = ViewModelProvider(this,allProductsViewModelFactory_Instance_RDS_InAllProductsActivity).get(
            CurrentWeatherViewModel::class.java)


        initUI_InAllProductsActivity()
        setUpRecyclerView_InAllProductsActivity()

        allProductsViewModel_Instance_InCrrentWeatherActivity.intLiveDataList_InAllProductsViewModel.observe(this){
                products ->
            adapter_Instance_InAllProductsActivity.setProductList_InAllProductsAdapter(products as ArrayList<Products>)
            adapter_Instance_InAllProductsActivity.notifyDataSetChanged()
        }

        allProductsViewModel_Instance_InCrrentWeatherActivity.getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()

    }

    private fun initUI_InAllProductsActivity(){
        recyclerView_Instance_InAllProductsActivity = findViewById(R.id.rv_allProducts)
    }

    private fun setUpRecyclerView_InAllProductsActivity(){
        layoutManager_Instance_InAllProductsActivity = LinearLayoutManager(this)
        layoutManager_Instance_InAllProductsActivity.orientation = RecyclerView.VERTICAL
        adapter_Instance_InAllProductsActivity = CurrentWeatherAdapter(this, ArrayList(),this)
        recyclerView_Instance_InAllProductsActivity.adapter = adapter_Instance_InAllProductsActivity
        recyclerView_Instance_InAllProductsActivity.layoutManager = layoutManager_Instance_InAllProductsActivity
    }

    override fun onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(product: Products) {
        allProductsViewModel_Instance_InCrrentWeatherActivity.insertProduct_InAllProductsViewModel(product)
    }
}
*/