package org.wit.livedive.activities

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.wit.livedive.MapActivity
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location


class DivePresenter(val view:DiveView) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var dive = DiveModel()
    var location = Location(19.2869, -81.3674, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("dive_edit")) {
            edit = true
            dive = view.intent.extras?.getParcelable<DiveModel>("dive_edit")!!
            view.showDive(dive)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        dive.title = title
        dive.description = description
        if (edit) {
            app.dives.update(dive)
        } else {
            app.dives.create(dive)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.dives.delete(dive)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (dive.zoom != 0f) {
            location.lat = dive.lat
            location.lng = dive.lng
            location.zoom = dive.zoom
        }
        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                dive.image = data.data.toString()
                view.showDive(dive)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                dive.lat = location.lat
                dive.lng = location.lng
                dive.zoom = location.zoom
            }
        }
    }
}