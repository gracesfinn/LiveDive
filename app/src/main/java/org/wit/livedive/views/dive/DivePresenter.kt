package org.wit.livedive.views.dive

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location
import org.wit.livedive.views.location.EditLocationView


class DivePresenter(view: BaseView) : BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var dive = DiveModel()
    var defaultLocation = Location(19.2869, -81.3674, 15f)
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
        view?.finish()
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
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
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
            }
        }
    }
    fun cacheDive (title: String, description: String) {
        dive.title = title;
        dive.description = description
    }
}