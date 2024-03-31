package com.example.weatherapplication.Settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.weatherapplication.R


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
            Log.i("Settings", "onViewCreated: Location: $selectedLocation")
        }

        radioGroupLanguage.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedLanguage = radioButton.text.toString()
            // Do something with the selected language
            Log.i("Settings", "onViewCreated: Language: $selectedLanguage")
        }

        radioGroupTemperature.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedTemperature = radioButton.text.toString()
            // Do something with the selected temperature
            Log.i("Settings", "onViewCreated: Temprature: $selectedTemperature")
        }

        radioGroupWindSpeed.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedWindSpeed = radioButton.text.toString()
            // Do something with the selected wind speed
            Log.i("Settings", "onViewCreated: Wind Speed: $selectedWindSpeed")
        }

        radioGroupNotification.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = view.findViewById(checkedId)
            val selectedNotification = radioButton.text.toString()
            // Do something with the selected notification
            Log.i("Settings", "onViewCreated: Notification: $selectedNotification ")
        }
    }
}
