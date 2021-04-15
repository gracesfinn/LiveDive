package org.wit.livedive.views.dive

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW
import org.wit.livedive.helpers.checkLocationPermissions
import org.wit.livedive.helpers.createDefaultLocationRequest
import org.wit.livedive.helpers.isPermissionGranted
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location


class DivePresenter(view: BaseView) : BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val locationRequest = createDefaultLocationRequest()

    var map: GoogleMap? = null

    var dive = DiveModel()
    var defaultLocation = Location(19.2869, -81.3674, 15f)
    var edit = false;
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)


    init {

        if (view.intent.hasExtra("dive_edit")) {
            edit = true
            dive = view.intent.extras?.getParcelable<DiveModel>("dive_edit")!!
            view.showDive(dive)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        dive.title = title
        dive.description = description
        doAsync {
            if (edit) {
                app.dives.update(dive)
            } else {
                app.dives.create(dive)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.dives.delete(dive)
        view?.finish()
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(dive.lat, dive.lng, dive.zoom))
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(dive.lat, dive.lng, dive.zoom)
            )
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                dive.image = data.data.toString()
                view?.showDive(dive)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                dive.lat = location.lat
                dive.lng = location.lng
                dive.zoom = location.zoom
                locationUpdate(dive.lat, dive.lng)
            }
        }
    }
    fun cacheDive (title: String, description: String) {
        dive.title = title;
        dive.description = description
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(dive.lat, dive.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        dive.lat = lat
        dive.lng = lng
        dive.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(dive.title).position(LatLng(dive.lat, dive.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(dive.lat, dive.lng), dive.zoom))
        view?.showDive(dive)
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }



    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
}