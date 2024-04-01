package com.example.weatherapplication.Settings

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Map.MapView.MapActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedPreferences.SharedPrefrences
import java.util.Locale


class SettingsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get references to all the RadioGroups
        val radioGroupLocation = view.findViewById<RadioGroup>(R.id.radioGroupLocation)
        val radioGroupLanguage = view.findViewById<RadioGroup>(R.id.radioGroupLanguage)
        val radioGroupTemperature = view.findViewById<RadioGroup>(R.id.radioGroupTemperature)
        val radioGroupWindSpeed = view.findViewById<RadioGroup>(R.id.radioGroupWindSpeed)

        // Set listeners for each RadioGroup
        radioGroupLocation.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedLocation = radioButton.text.toString()


            val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString(Utils.LOCATION_KEY, selectedLocation)
            editor?.apply()


            if(selectedLocation == Utils.MAP){
                val intent = Intent(requireActivity(), MapActivity::class.java)
                startActivity(intent)
            }

            Log.i("Settings", "onViewCreated: Location: $selectedLocation")
        }

        radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedLanguage = radioButton.text.toString()

            val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString(Utils.LANGUAGE_KEY, selectedLanguage)
            editor?.apply()
            Log.i("Settings", "onViewCreated: Language: $selectedLanguage")
        }

        radioGroupTemperature.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedTemperature = radioButton.text.toString()

            val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString(Utils.TEMPRATURE_KEY, selectedTemperature)
            editor?.apply()

            Log.i("Settings", "onViewCreated: Temprature: $selectedTemperature")
        }

        radioGroupWindSpeed.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedWindSpeed = radioButton.text.toString()


            val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString(Utils.WINDSPEED_KEY, selectedWindSpeed)
            editor?.apply()

            Log.i("Settings", "onViewCreated: Wind Speed: $selectedWindSpeed")
        }


    }

    fun getLang(): String{
        val sharedPreferences = context?.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        var sp_lang_value =sharedPreferences?.getString(Utils.LANGUAGE_KEY,null)

        if(sp_lang_value != null){
            if(sp_lang_value == Utils.ARABIC){
                return "ar"
            }
        }
        return "en"
    }

    private fun setLocale() {
        // Retrieve language preference
        val lang = getLang()
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        // Set layout direction based on the locale
        val layoutDirection = if (lang == "ar") View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
        requireActivity().window.decorView.layoutDirection = layoutDirection

    }
}
