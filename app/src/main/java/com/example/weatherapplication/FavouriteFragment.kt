package com.example.weatherapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton


class FavouriteFragment : Fragment() {

    lateinit var floatingActionButton_map: FloatingActionButton
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

}