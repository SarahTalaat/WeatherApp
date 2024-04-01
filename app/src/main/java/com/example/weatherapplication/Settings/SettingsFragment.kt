package com.example.weatherapplication.Settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.R
import com.example.weatherapplication.SharedPreferences.SharedPrefrences


class SettingsFragment : Fragment() {

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
        val radioGroupNotification = view.findViewById<RadioGroup>(R.id.radioGroupNotification)

        // Set listeners for each RadioGroup
        radioGroupLocation.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedLocation = radioButton.text.toString()
            // Do something with the selected location
            SharedPrefrences.getInstance(requireContext()).setLocationValue(Utils.LOCATION_KEY,selectedLocation)

//            if (selectedLocation=="GPS"){
//
//            }else if(selectedLocation == "Map"){
//
//            }

            Log.i("Settings", "onViewCreated: Location: $selectedLocation")
        }

        radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedLanguage = radioButton.text.toString()
            SharedPrefrences.getInstance(requireContext()).setLocationValue(Utils.LANGUAGE_KEY,selectedLanguage)

            // Do something with the selected language

//            if(selectedLanguage == "English"){
//
//            }else if(selectedLanguage == "Arabic"){
//
//            }
            Log.i("Settings", "onViewCreated: Language: $selectedLanguage")
        }

        radioGroupTemperature.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedTemperature = radioButton.text.toString()
            SharedPrefrences.getInstance(requireContext()).setLocationValue(Utils.TEMPRATURE_KEY,selectedTemperature)

            // Do something with the selected temperature

//            if(selectedTemperature == "Celsius"){
//
//            }else if (selectedTemperature == "Kelvin"){
//
//            }else if(selectedTemperature == "Fahrenheit"){
//
//            }

            Log.i("Settings", "onViewCreated: Temprature: $selectedTemperature")
        }

        radioGroupWindSpeed.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedWindSpeed = radioButton.text.toString()
            // Do something with the selected wind speed
            SharedPrefrences.getInstance(requireContext()).setLocationValue(Utils.WINDSPEED_KEY,selectedWindSpeed)

//            if(selectedWindSpeed == "Meter/Sec"){
//
//            }else if(selectedWindSpeed == "Mile/Hour"){
//
//            }

            Log.i("Settings", "onViewCreated: Wind Speed: $selectedWindSpeed")
        }

        radioGroupNotification.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedNotification = radioButton.text.toString()
            // Do something with the selected notification
            SharedPrefrences.getInstance(requireContext()).setLocationValue(Utils.NOTIFICATION_KEY,selectedNotification)

//            if(selectedNotification == "Enable"){
//
//            }else if(selectedNotification == "Disable"){
//
//            }

            Log.i("Settings", "onViewCreated: Notification: $selectedNotification ")
        }
    }
}
