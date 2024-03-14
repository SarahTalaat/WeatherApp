package com.example.productsmvvm.AllProducts.AllProductsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.ProductsRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllProductsViewModel(private val productsRepositoryInterfaceInstance_ConstructorParameter_InAllProductsViewModel: ProductsRepositoryInterface):ViewModel() {


    private var productsMutableLiveDataList_InAllProductsViewModel: MutableLiveData<List<Products>> = MutableLiveData<List<Products>>()
    //products
    val productsLiveDataList_InAllProductsViewModel: LiveData<List<Products>> = productsMutableLiveDataList_InAllProductsViewModel

    init {
        getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()
    }

    fun insertProduct_InAllProductsViewModel(product: Products){
        viewModelScope.launch(Dispatchers.IO){
            productsRepositoryInterfaceInstance_ConstructorParameter_InAllProductsViewModel.insertProduct_FromLDS_InProductsRepository(product)
            getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()
        }
    }

    fun getAllRemoteProducts_FromRetrofit_InAllProductsViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            productsMutableLiveDataList_InAllProductsViewModel.postValue(productsRepositoryInterfaceInstance_ConstructorParameter_InAllProductsViewModel.getAllProducts_FromRDS_InProductsRepository())
        }
    }

}