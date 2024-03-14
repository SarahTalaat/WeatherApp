package com.example.productsmvvm.Model

import com.example.productsmvvm.Database.ProductsLocalDataSourceInterface
import com.example.productsmvvm.Network.ProductsRemoteDataSourceInterface
import kotlinx.coroutines.flow.Flow

//Singleton
class ProductsRepositoryImplementation private constructor(
    private var productsRemoteDataSourceInterface_Instance: ProductsRemoteDataSourceInterface,
    private var productsLocalDataSourceInterface_Instance: ProductsLocalDataSourceInterface
) : ProductsRepositoryInterface
{
    companion object{
        private var instance: ProductsRepositoryImplementation? = null
        fun getProductsRepositoryImplementationInstance(
            productsRemoteDataSourceInterface: ProductsRemoteDataSourceInterface,
            productsLocalDataSourceInterface: ProductsLocalDataSourceInterface
        ): ProductsRepositoryImplementation{
            return instance?: synchronized(this){
                val temp = ProductsRepositoryImplementation(
                    productsRemoteDataSourceInterface,
                    productsLocalDataSourceInterface
                )
                instance = temp
                temp
            }
        }
    }

    override suspend fun getAllProducts_FromRDS_InProductsRepository(): List<Products> {
        return productsRemoteDataSourceInterface_Instance.getAllProductsOverNetwork_InRDS()
    }

    override suspend fun getAllStoredProducts_FromLDS_InProductsRepository(): Flow<List<Products>> {
        return productsLocalDataSourceInterface_Instance.getAllStoredProductsFromDatabase_InLDS()
    }

    override suspend fun insertProduct_FromLDS_InProductsRepository(product: Products) {
        return productsLocalDataSourceInterface_Instance.insertProductIntoDatabase_InLDS(product)
    }

    override suspend fun deleteProduct_FromLDS_InProductsRepository(product: Products) {
        return productsLocalDataSourceInterface_Instance.deleteProductFromDatabase_InLDS(product)
    }


}