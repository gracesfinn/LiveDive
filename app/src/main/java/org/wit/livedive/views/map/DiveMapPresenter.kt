package org.wit.livedive.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.models.DiveModel

class DiveMapPresenter (view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, dives: List<DiveModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        dives.forEach {
                val loc = LatLng(it.location.lat, it.location.lng)
                val options = MarkerOptions().title(it.title).position(loc)
                map.addMarker(options).tag = it.id
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        doAsync {
            val dive = app.dives.findById(tag)
            uiThread {
                if (dive != null) view?.showDive(dive)
            }
        }
    }

    fun loadDives() {
        doAsync {
            val dives = app.dives.findAll()
            uiThread {
                view?.showDives(dives)
            }
        }
    }
}