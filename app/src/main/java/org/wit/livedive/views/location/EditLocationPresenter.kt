package org.wit.livedive.views.location

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.models.Location

class EditLocationPresenter (view: BaseView) : BasePresenter(view) {

    var location = Location()

    init {
        location = view.intent.extras?.getParcelable<Location>("location")!!
    }

    fun doConfigureMap(map: GoogleMap) {
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Dive")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        view?.showLocation(location);
    }

    fun doUpdateLocation(lat: Double, lng: Double) {
        location.lat = lat
        location.lng = lng

    }

    fun doOnBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view?.setResult(Activity.RESULT_OK, resultIntent)
        view?.finish()
    }
    fun doSave() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view?.setResult(0, resultIntent)
        view?.finish()
    }

    fun doUpdateMarker(marker: Marker) {
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
    }
}