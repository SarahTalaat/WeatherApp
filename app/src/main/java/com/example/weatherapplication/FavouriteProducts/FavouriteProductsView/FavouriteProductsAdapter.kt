package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView

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

class FavouriteProductsAdapter: RecyclerView.Adapter<FavouriteProductsAdapter.MyViewHolder_InFavouriteProductsAdapter> {

    private final var tab: String = "team"

    var context_Instance_InFavouriteProductsAdapter: Context
    var productsArrayList_InFavouriteProductsAdapter: ArrayList<Products>
    var onFavouriteClickListenerInterface_Instance_InFavouriteProductsAdapter: OnFavouriteClickListenerInterface

    public constructor(context_Instance_ConstructorParameter_InFavouriteProductsAdapter: Context, productsArrayList_ConstructorParameter_InFavouriteProductsAdapter: ArrayList<Products>, onFavouriteClickListenerInterfaceInstance_ConstructorParameter_InFavouriteProductsAdapter: OnFavouriteClickListenerInterface){
        this.context_Instance_InFavouriteProductsAdapter=context_Instance_ConstructorParameter_InFavouriteProductsAdapter
        this.productsArrayList_InFavouriteProductsAdapter=productsArrayList_ConstructorParameter_InFavouriteProductsAdapter
        this.onFavouriteClickListenerInterface_Instance_InFavouriteProductsAdapter = onFavouriteClickListenerInterfaceInstance_ConstructorParameter_InFavouriteProductsAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InFavouriteProductsAdapter {
        var inflater: LayoutInflater = context_Instance_InFavouriteProductsAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.activity_favourite_products_details,parent,false)
        var myViewHolder_InFavouriteProductsAdapter: MyViewHolder_InFavouriteProductsAdapter = MyViewHolder_InFavouriteProductsAdapter(view)
        return  myViewHolder_InFavouriteProductsAdapter
    }

    override fun getItemCount(): Int {
        return productsArrayList_InFavouriteProductsAdapter.size
    }

    fun setProductList_InFavouriteProductsAdapter(productsArrayList: ArrayList<Products>){
        Log.i("TAG", "setProductList_InFavouriteProductsAdapter: productArrayList :" + productsArrayList)
        this.productsArrayList_InFavouriteProductsAdapter = productsArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder_InFavouriteProductsAdapter: RecyclerView.ViewHolder {

            var tv_title_value_favouriteProducts: TextView
            var tv_descroption_value_favouriteProducts: TextView
            var tv_id_value_favouriteProducts: TextView
            var tv_price_value_favouriteProducts: TextView
            var img_product_favouriteProducts: ImageView
            var btn_removeFromFavourite_favouriteProducts: Button


        constructor(@NonNull itemView: View): super(itemView){
                tv_title_value_favouriteProducts = itemView.findViewById(R.id.tv_title_value_favouriteProducts)
                tv_descroption_value_favouriteProducts = itemView.findViewById(R.id.tv_description_value_favouriteProducts)
                tv_id_value_favouriteProducts = itemView.findViewById(R.id.tv_id_value_favouriteProducts)
                tv_price_value_favouriteProducts = itemView.findViewById(R.id.tv_id_value_favouriteProducts)
                img_product_favouriteProducts = itemView.findViewById(R.id.img_product_favouriteProducts)
                btn_removeFromFavourite_favouriteProducts = itemView.findViewById(R.id.btn_removeFromFavourite_favouriteProducts)
            }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InFavouriteProductsAdapter, position: Int) {
        var imageUrl_InMyViewHolder_InFavouriteProductsAdapter: String = productsArrayList_InFavouriteProductsAdapter.get(position).thumbnail
        Glide.with(context_Instance_InFavouriteProductsAdapter).load(imageUrl_InMyViewHolder_InFavouriteProductsAdapter).into(holder.img_product_favouriteProducts)

        holder.tv_title_value_favouriteProducts.setText(productsArrayList_InFavouriteProductsAdapter.get(position).title)
        holder.tv_price_value_favouriteProducts.setText(productsArrayList_InFavouriteProductsAdapter.get(position).price.toString())
        holder.tv_descroption_value_favouriteProducts.setText(productsArrayList_InFavouriteProductsAdapter.get(position).description)
        holder.tv_id_value_favouriteProducts.setText(productsArrayList_InFavouriteProductsAdapter.get(position).id.toString())

        holder.btn_removeFromFavourite_favouriteProducts.setOnClickListener(){
            onFavouriteClickListenerInterface_Instance_InFavouriteProductsAdapter.onClick_DeleteProductFromFavouriteProductsActivity_InOnFavouriteClickListenerInterface(productsArrayList_InFavouriteProductsAdapter.get(position))
        }

    }

}