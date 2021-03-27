package org.wit.livedive.views.divelist

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel
import org.wit.livedive.views.dive.DiveView
import org.wit.livedive.views.map.DiveMapView

class DiveListPresenter (view: BaseView) : BasePresenter(view) {

    fun doAddDive() {
        view?.navigateTo(VIEW.DIVE)
    }


    fun doEditDive(dive: DiveModel) {
        view?.navigateTo(VIEW.DIVE, 0, "dive_edit", dive)
    }

    fun doShowDivesMap() {
        view?.navigateTo(VIEW.MAPS)
    }
    fun loadDives() {
        view?.showDives(app.dives.findAll())
    }
}