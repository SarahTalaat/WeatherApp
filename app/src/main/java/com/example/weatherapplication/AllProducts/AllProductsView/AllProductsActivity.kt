package com.example.productsmvvm.AllProducts.AllProductsView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.AllProducts.AllProductsViewModel.AllProductsViewModel
import com.example.productsmvvm.AllProducts.AllProductsViewModel.AllProductsViewModelFactory_RDS
import com.example.productsmvvm.Database.ProductsLocalDataSourceImplementation
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModel
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel.FavouriteProductsViewModelFactory_LDS
import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.ProductsRepositoryImplementation
import com.example.productsmvvm.Network.ProductsRemoteDataSourceImplementation
import com.example.productsmvvm.R
import java.util.ArrayList

class AllProductsActivity : AppCompatActivity() , OnAllProductsClickListenerInterface{
    private lateinit var favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity: FavouriteProductsViewModelFactory_LDS
    private lateinit var allProductsViewModelFactory_Instance_RDS_InAllProductsActivity: AllProductsViewModelFactory_RDS
    private lateinit var favouriteProductsViewModel_Instance_InAllProductsActivity: FavouriteProductsViewModel
    private lateinit var allProductsViewModel_Instance_InAllProductsActivity: AllProductsViewModel
    private lateinit var recyclerView_Instance_InAllProductsActivity: RecyclerView
    private lateinit var adapter_Instance_InAllProductsActivity: AllProductsAdapter
    private lateinit var layoutManager_Instance_InAllProductsActivity: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products)

        favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity = FavouriteProductsViewModelFactory_LDS(
            ProductsRepositoryImplementation.getProductsRepositoryImplementationInstance(
            ProductsRemoteDataSourceImplementation.getProductsRemoteDataSourceImplementationInstance(),
            ProductsLocalDataSourceImplementation(this)
            )
        )

        allProductsViewModelFactory_Instance_RDS_InAllProductsActivity = AllProductsViewModelFactory_RDS(
            ProductsRepositoryImplementation.getProductsRepositoryImplementationInstance(
                ProductsRemoteDataSourceImplementation.getProductsRemoteDataSourceImplementationInstance(),
                ProductsLocalDataSourceImplementation(this)
            )
        )

        favouriteProductsViewModel_Instance_InAllProductsActivity = ViewModelProvider(this,favouriteProductsViewModelFactory_Instance_LDS_InAllProductsActivity).get(
            FavouriteProductsViewModel::class.java)

        allProductsViewModel_Instance_InAllProductsActivity = ViewModelProvider(this,allProductsViewModelFactory_Instance_RDS_InAllProductsActivity).get(
            AllProductsViewModel::class.java)


        initUI_InAllProductsActivity()
        setUpRecyclerView_InAllProductsActivity()

        allProductsViewModel_Instance_InAllProductsActivity.productsLiveDataList_InAllProductsViewModel.observe(this){
                products ->
            adapter_Instance_InAllProductsActivity.setProductList_InAllProductsAdapter(products as ArrayList<Products>)
            adapter_Instance_InAllProductsActivity.notifyDataSetChanged()
        }

        allProductsViewModel_Instance_InAllProductsActivity.getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()

    }

    private fun initUI_InAllProductsActivity(){
        recyclerView_Instance_InAllProductsActivity = findViewById(R.id.rv_allProducts)
    }

    private fun setUpRecyclerView_InAllProductsActivity(){
        layoutManager_Instance_InAllProductsActivity = LinearLayoutManager(this)
        layoutManager_Instance_InAllProductsActivity.orientation = RecyclerView.VERTICAL
        adapter_Instance_InAllProductsActivity = AllProductsAdapter(this, ArrayList(),this)
        recyclerView_Instance_InAllProductsActivity.adapter = adapter_Instance_InAllProductsActivity
        recyclerView_Instance_InAllProductsActivity.layoutManager = layoutManager_Instance_InAllProductsActivity
    }

    override fun onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(product: Products) {
        allProductsViewModel_Instance_InAllProductsActivity.insertProduct_InAllProductsViewModel(product)
    }
}