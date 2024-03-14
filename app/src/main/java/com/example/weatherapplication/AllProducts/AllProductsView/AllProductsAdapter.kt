package com.example.productsmvvm.AllProducts.AllProductsView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.R
import java.util.ArrayList

class AllProductsAdapter: RecyclerView.Adapter<AllProductsAdapter.MyViewHolder_InAllProductsAdapter>  {


    private final var tab: String = "team"

    var context_Instance_InAllProductsAdapter: Context
    var productsArrayList_InAllProductsAdapter: ArrayList<Products>
    var onAllProductsClickListenerInterface_Instance_InAllProductsAdapter: OnAllProductsClickListenerInterface

    public constructor(context_Instance_ConstructorParameter_InAllProductsAdapter: Context, productsArrayList_ConstructorParameter_InAllProductsAdapter: ArrayList<Products>, onAllProductsClickListenerInterfaceInstance_ConstructorParameter_InAllProductsAdapter: OnAllProductsClickListenerInterface){
        this.context_Instance_InAllProductsAdapter=context_Instance_ConstructorParameter_InAllProductsAdapter
        this.productsArrayList_InAllProductsAdapter=productsArrayList_ConstructorParameter_InAllProductsAdapter
        this.onAllProductsClickListenerInterface_Instance_InAllProductsAdapter = onAllProductsClickListenerInterfaceInstance_ConstructorParameter_InAllProductsAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InAllProductsAdapter {
        var inflater: LayoutInflater = context_Instance_InAllProductsAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.activity_all_products_details,parent,false)
        var myViewHolder_InAllProductsAdapter: MyViewHolder_InAllProductsAdapter = MyViewHolder_InAllProductsAdapter(view)
        return  myViewHolder_InAllProductsAdapter
    }

    override fun getItemCount(): Int {
        return productsArrayList_InAllProductsAdapter.size
    }

    fun setProductList_InAllProductsAdapter(productsArrayList: ArrayList<Products>){
        Log.i("TAG", "setProductList_InAllProductsAdapter: productArrayList :" + productsArrayList)
        this.productsArrayList_InAllProductsAdapter = productsArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder_InAllProductsAdapter: RecyclerView.ViewHolder {

        var tv_title_value_allProducts: TextView
        var tv_descroption_value_allProducts: TextView
        var tv_id_value_allProducts: TextView
        var tv_price_value_allProducts: TextView
        var img_product_allProducts: ImageView
        var btn_addToFavourite_allProducts: Button


        constructor(@NonNull itemView: View): super(itemView){
            tv_title_value_allProducts = itemView.findViewById(R.id.tv_title_value_allProducts)
            tv_descroption_value_allProducts = itemView.findViewById(R.id.tv_description_value_allProducts)
            tv_id_value_allProducts = itemView.findViewById(R.id.tv_id_value_allProducts)
            tv_price_value_allProducts = itemView.findViewById(R.id.tv_id_value_allProducts)
            img_product_allProducts = itemView.findViewById(R.id.img_product_allProducts)
            btn_addToFavourite_allProducts = itemView.findViewById(R.id.btn_addToFavourite_allProducts)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InAllProductsAdapter, position: Int) {
        var imageUrl_InMyViewHolder_InAllProductsAdapter: String = productsArrayList_InAllProductsAdapter.get(position).thumbnail
        Glide.with(context_Instance_InAllProductsAdapter).load(imageUrl_InMyViewHolder_InAllProductsAdapter).into(holder.img_product_allProducts)

        holder.tv_title_value_allProducts.setText(productsArrayList_InAllProductsAdapter.get(position).title)
        holder.tv_price_value_allProducts.setText(productsArrayList_InAllProductsAdapter.get(position).price.toString())
        holder.tv_descroption_value_allProducts.setText(productsArrayList_InAllProductsAdapter.get(position).description)
        holder.tv_id_value_allProducts.setText(productsArrayList_InAllProductsAdapter.get(position).id.toString())

        holder.btn_addToFavourite_allProducts.setOnClickListener(){
            onAllProductsClickListenerInterface_Instance_InAllProductsAdapter.onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(productsArrayList_InAllProductsAdapter.get(position))
        }

    }
}