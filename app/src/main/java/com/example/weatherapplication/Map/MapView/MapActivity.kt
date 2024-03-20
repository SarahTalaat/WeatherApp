package com.example.weatherapplication.Map.MapView


import android.content.Intent
import android.os.Bundle

import android.graphics.Rect
import android.location.Geocoder
import android.location.GpsStatus
import android.util.Log
import android.view.MotionEvent

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherView.FavouriteCityWeatherActivity
import com.example.weatherapplication.Map.MapViewModel.MapViewModel
import com.example.weatherapplication.Map.MapViewModel.MapViewModelFactory_LDS
import com.example.weatherapplication.Model.Model_FavouriteCity
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ActivityMapBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.Locale

class MapActivity : AppCompatActivity(), MapListener, GpsStatus.Listener ,
    OnFavouriteCityClickListenerInterface {


    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var mMyLocationOverlay: MyLocationNewOverlay
    lateinit var binding: ActivityMapBinding
    private  lateinit var pinMarker: Marker
    lateinit var cityName: String
    lateinit var mapWeatherViewModel_Instance_InMapActivity: MapViewModel
    lateinit var mapWeatherViewModelFactory_LDS_Instance_InMapActivity: MapViewModelFactory_LDS

    var lon: Double=0.0
    var lat: Double=0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mapWeatherViewModelFactory_LDS_Instance_InMapActivity = MapViewModelFactory_LDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getCurrentWeatherRemoteDataSourceImplementation_Instance(),
                WeatherLocalDataSourceImplementation(this)
            )
        )

        mapWeatherViewModel_Instance_InMapActivity = ViewModelProvider(this, mapWeatherViewModelFactory_LDS_Instance_InMapActivity ).get(
            MapViewModel::class.java)



        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())


        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                controller.setCenter(mMyLocationOverlay.myLocation);
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }
        // val mapPoint = GeoPoint(latitude, longitude)

        controller.setZoom(6.0)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)

        mMap.addMapListener(this)


        pinMarker = Marker(mMap)
        binding.osmmap.overlays.add(TapOverlay())


    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    override fun onGpsStatusChanged(event: Int) {


        TODO("Not yet implemented")
    }


    fun findCityName(lat:Double , lon: Double){
        val geocoder = Geocoder(this, Locale.getDefault())
        val fullAddress = geocoder.getFromLocation(lat,lon,1)
        if(fullAddress != null){
            if(fullAddress.isNotEmpty()){
                val address = fullAddress.get(0)
                cityName = address.adminArea
                Log.i("TAG", "findCityName_InMapActivity: cityname = " + cityName)

                var modelModel_FavouriteCity = Model_FavouriteCity(lat.toString(),lon.toString(),cityName)
                onClick_insertFavouriteCityToFavouriteActivity_InFavouriteCityClickListenerInterface(modelModel_FavouriteCity)

                val intent = Intent(this, FavouriteCityWeatherActivity::class.java)
                intent.putExtra(Utils.FAVOURITE_CITY_LATITUDE, "$lat")
                intent.putExtra(Utils.FAVOURITE_CITY_LONGITUDE, "$lon")
                intent.putExtra(Utils.FAVOURITE_CITY_NAME,"$cityName")
                startActivity(intent)

            }
        }
    }

    private  inner class TapOverlay: Overlay(){
        override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
            val point = mapView?.projection?.fromPixels(e?.x?.toInt() ?: 0 , e?.y?.toInt() ?: 0)
            binding.osmmap.overlays.remove(pinMarker)
            pinMarker = Marker(mapView)
            pinMarker?.position = point as GeoPoint
            binding.osmmap.overlays.add(pinMarker)
            binding.osmmap.invalidate()

            binding.btnConfirmLocation.setOnClickListener() {
                Log.i(
                    "TAG",
                    "onSingleTapConfirmed_InInnerClass_InMapActivity: Latitude:  ${point.latitude}  Longitude: ${point.longitude}")

                if (point.latitude != null && point.longitude != null) {
                    var latitude = point.latitude
                    var longitude = point.longitude
                    findCityName(latitude,longitude)
                }
/*
                if (point.latitude != null && point.longitude != null) {
                     val geoUtils = GeoUtils(this@MapActivity)
                     val address = geoUtils.getAddress(point?.latitude, point?.longitude)

                    Log.i("TAG", "onSingleTapConfirmed_InInnerClass_InMapActivity: Address: " +address)
                }

 */


            }

            return true
        }
    }

    override fun onClick_insertFavouriteCityToFavouriteActivity_InFavouriteCityClickListenerInterface(city: Model_FavouriteCity) {
        mapWeatherViewModel_Instance_InMapActivity.insertFavouriteCity_InMapViewModel(city)

    }


}