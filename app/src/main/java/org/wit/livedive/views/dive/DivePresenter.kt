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
import org.wit.livedive.*
import org.wit.livedive.helpers.checkLocationPermissions
import org.wit.livedive.helpers.createDefaultLocationRequest
import org.wit.livedive.helpers.isPermissionGranted
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location


class DivePresenter(view: BaseView) : BasePresenter(view) {


    var map: GoogleMap? = null
    var dive = DiveModel()
    var defaultLocation = Location(19.2869, -81.3674, 15f)
    var edit = false;
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var locationManualyChanged = false;


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
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    if(!locationManualyChanged) {
                        locationUpdate(Location(l.latitude, l.longitude))
                    }
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    fun cacheDive (title: String,
                   description: String,
                   dayOfMonth: Int,
                   month: Int,
                   year: Int,
                   maxDepth: String,
                   mins: String,
                   weight: String,
                   weather: String,
                   ocean: String,
                   wildlife: String,
                   POI : String,
                   additionalNotes : String) {
        dive.title = title;
        dive.description = description
        dive.dayVisited = dayOfMonth
        dive.monthVisited = month
        dive.yearVisited = year
        dive.maxDepth = maxDepth
        dive.mins= mins
        dive.weight = weight
        dive.weather = weather
        dive.ocean = ocean
        dive.wildlife = wildlife
        dive.poi = POI
        dive.additionalNotes = additionalNotes
    }


    fun locationUpdate(location: Location) {
        dive.location = location
        dive.location.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(dive.title).position(LatLng(dive.location.lat, dive.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(dive.location.lat, dive.location.lng), dive.location.zoom))
        view?.showLocation(dive.location)
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(dive.location)
    }

    fun doAddOrSave(
        title: String,
        description: String,
        dayOfMonth: Int,
        month: Int,
        year: Int,
        maxDepth: String,
        mins: String,
        weight: String,
        weather: String,
        ocean: String,
        wildlife: String,
        POI : String,
        additionalNotes : String
    ) {
        dive.title = title
        dive.description = description
        dive.dayVisited = dayOfMonth
        dive.monthVisited = month
        dive.yearVisited = year
        dive.maxDepth = maxDepth
        dive.mins= mins
        dive.weight = weight
        dive.weather = weather
        dive.ocean = ocean
        dive.wildlife = wildlife
        dive.poi = POI
        dive.additionalNotes = additionalNotes
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
        doAsync {
            app.dives.delete(dive)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }

    fun doSelectImageWildlife(){
        showImagePicker(view!!, IMAGE_WILDLIFE_REQUEST)
    }

    fun doSelectImagePOI(){
        showImagePicker(view!!, IMAGE_POI_REQUEST)
    }

    fun doSetLocation() {
        locationManualyChanged = true;
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(dive.location.lat, dive.location.lng, dive.location.zoom))

    }

    fun doCheckWetsuit(wetsuit:Boolean)
    {
        dive.wetsuit = wetsuit
    }

    fun doCheckAir(air:Boolean)
    {
        dive.air = air
    }

    fun doCheckNitrox(nitrox:Boolean)
    {
        dive.nitrox = nitrox
    }

    fun doCheckRatingBar(rating:Float)
    {
        dive.rating = rating
    }

    fun doCheckFavourite(
        favourite : Boolean
    ){
        dive.favourite = favourite
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    dive.image = data.getData().toString()
                    view?.showDive(dive)
                }
            }
            IMAGE_WILDLIFE_REQUEST -> {
                if (data != null){
                    dive.wildlifeImage = data.getData().toString()
                    view?.showDive(dive)
                }
            }
            IMAGE_POI_REQUEST -> {
                if (data != null){
                    dive.poiImage = data.getData().toString()
                    view?.showDive(dive)
                }
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                dive.location  = location
                locationUpdate(location)
            }
        }
    }
}