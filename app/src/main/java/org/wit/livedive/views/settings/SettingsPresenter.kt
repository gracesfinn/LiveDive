package org.wit.livedive.views.settings

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.wit.livedive.BasePresenter
import org.wit.livedive.BaseView
import org.wit.livedive.VIEW

class SettingsPresenter  (view: BaseView) : BasePresenter(view){

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    val emailAddress = user!!.email
    lateinit var db: DatabaseReference

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

    /*fun doLoadProfile(name: String, numDives: String, certification: String, certNum: String ){
        val userReference = db.child(user?.uid!!)

        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user.name = snapshot.child("name").value
            }
        })

    } */

    fun doCancel() {
        view?.finish()
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

}