package com.example.weatherapplication


import android.os.Bundle

import android.app.ProgressDialog
import android.graphics.Rect
import android.location.GpsStatus
import android.location.Location
import android.util.Log
import android.view.MotionEvent

import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.databinding.ActivityMapBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
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

class MapActivity : AppCompatActivity(), MapListener, GpsStatus.Listener {


    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var mMyLocationOverlay: MyLocationNewOverlay
    lateinit var binding: ActivityMapBinding
    private  lateinit var pinMarker: Marker

    var lon: Double=0.0
    var lat: Double=0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    private  inner class TapOverlay: Overlay(){
        override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
            val point = mapView?.projection?.fromPixels(e?.x?.toInt() ?: 0 , e?.y?.toInt() ?: 0)
            binding.osmmap.overlays.remove(pinMarker)
            pinMarker = Marker(mapView)
            pinMarker?.position = point as GeoPoint
            binding.osmmap.overlays.add(pinMarker)
            binding.osmmap.invalidate()

            binding.btnConfirmLocation.setOnClickListener(){
                Log.i("TAG", "onSingleTapConfirmed_InInnerClass_InMapActivity: Latitude:  ${point.latitude}  Longitude: ${point.longitude}")

            }
            return true
        }
    }


}