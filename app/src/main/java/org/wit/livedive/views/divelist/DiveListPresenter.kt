package org.wit.livedive.views.divelist

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*
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

    fun doSetFavourite(){

    }

    fun doShowFavourites() {
        view?.navigateTo(VIEW.FAVOURITE)
    }

    fun doUpdateUser() {
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun loadDives() {
        doAsync {
            val dives = app.dives.findAll()
            var favDives = mutableListOf<DiveModel>()
            uiThread {
                if(view!!.intent.hasExtra("favourite"))
                {
                    for(dive in dives){
                        if (dive.favourite == true){
                            favDives.add(dive)
                        }
                    }
                    view?.showDives(favDives)
                }
                else {
                    view?.showDives(dives)
                }
            }
        }
    }

    fun doShowList() {
        view!!.intent.removeExtra("favourite")
        view?.navigateTo(VIEW.LIST)
        Log.d(ContentValues.TAG, "Back to home")
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.dives.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}