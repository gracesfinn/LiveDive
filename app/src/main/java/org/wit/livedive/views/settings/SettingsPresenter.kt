package org.wit.livedive.views.settings

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW

class SettingsPresenter  (view: BaseView) : BasePresenter(view){

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    val emailAddress = user!!.email

    fun doUpdateEmail(email: String) {
        user!!.updateEmail(email)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d(ContentValues.TAG, "User email address updated")
                }
            }

    }

    fun doSendPasswordReset(){
        if (emailAddress != null) {
            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "Email sent.")
                    }
                }
        }


    }

    fun doCancel() {
        view?.finish()
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

}