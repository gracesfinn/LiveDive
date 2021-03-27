package org.wit.livedive.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel

class DiveMapPresenter (view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, dives: List<DiveModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        dives.forEach {
                val loc = LatLng(it.lat, it.lng)
                val options = MarkerOptions().title(it.title).position(loc)
                map.addMarker(options).tag = it.id
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val dive = app.dives.findById(tag)
        if (dive != null) view?.showDive(dive)
    }

    fun loadDives() {
        view?.showDives(app.dives.findAll())
    }
}