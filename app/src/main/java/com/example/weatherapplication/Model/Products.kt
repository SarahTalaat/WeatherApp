/*
package com.example.productsmvvm.Model

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products_table")
class Products(
    @PrimaryKey
    @NonNull
    var id: Int,
    var title: String ,
    var description:String,
    var price:Int,
    var thumbnail: String){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Products

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (price != other.price) return false
        return thumbnail == other.thumbnail
    }

}

 */