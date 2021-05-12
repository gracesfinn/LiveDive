package org.wit.livedive.views.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import org.jetbrains.anko.toast
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW
import org.wit.livedive.models.UserModel
import org.wit.livedive.models.firebase.DiveFireStore

class RegisterPresenter (view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DiveFireStore? = null

    init {
        if (app.dives is DiveFireStore) {
            fireStore = app.dives as DiveFireStore
        }
    }

    fun doSignUp() {

                fireStore!!.fetchDives {
                    view?.navigateTo(VIEW.LIST)
                }

        }

}