package com.example.weatherapplication

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object TemperatureUnitManager {
    private val _temperatureUnitFlow = MutableSharedFlow<String>()
    val temperatureUnitFlow: SharedFlow<String> = _temperatureUnitFlow

    suspend fun updateTemperatureUnit(unit: String) {
        Log.i("Settings", "updateTemperatureUnit: Temperature Mnager: unit: $unit ")
        _temperatureUnitFlow.emit(unit)
    }
}
