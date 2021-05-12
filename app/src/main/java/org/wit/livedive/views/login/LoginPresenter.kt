package org.wit.livedive.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW
import org.wit.livedive.models.firebase.DiveFireStore

class LoginPresenter (view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DiveFireStore? = null

    init {
        if (app.dives is DiveFireStore) {
            fireStore = app.dives as DiveFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if(fireStore != null) {
                    fireStore!!.fetchDives {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp() {
                view?.navigateTo(VIEW.REGISTER)
    }



}