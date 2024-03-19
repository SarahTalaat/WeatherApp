package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView
/*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModel
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModelFactory_LDS
import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.productsmvvm.R
import java.util.ArrayList

class FavouriteProductsActivity : AppCompatActivity() , OnFavouriteClickListenerInterface{

    private lateinit var favouriteProductsViewModelFactory_Instance_LDS_InFavouriteProductsActivity: FavouriteProductsViewModelFactory_LDS
    private lateinit var favouriteProductsViewModel_Instance_InFavouriteProductsActivity: FavouriteProductsViewModel
    private lateinit var recyclerView_Instance_InFavouriteProductsActivity: RecyclerView
    private lateinit var adapter_Instance_InFavouriteProductsActivity: FavouriteProductsAdapter
    private lateinit var layoutManager_Instance_InFavouriteProductsActivity: LinearLayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_products)

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

 */