package org.wit.livedive.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_dive_maps.*
import kotlinx.android.synthetic.main.activity_divelist.*
import kotlinx.android.synthetic.main.activity_divelist.toolbar
import org.wit.livedive.R
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.main.MainApp


class DiveMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive_maps)
        setSupportActionBar(toolbar)
        toolbar.title = title
        mapView.onCreate(savedInstanceState);
        app = application as MainApp

        mapView.getMapAsync {
            map = it
            configureMap()
        }
    }


    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        app.dives.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val dive = app.dives.findById(tag)
        currentTitle.text = dive!!.title
        currentDescription.text = dive!!.description
        currentImage.setImageBitmap(readImageFromPath(this, dive.image))
        return true
    }

}