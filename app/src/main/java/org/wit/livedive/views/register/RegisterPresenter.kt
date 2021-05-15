package org.wit.livedive.views.register

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.toast
import org.wit.livedive.*
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.models.UserModel
import org.wit.livedive.models.firebase.DiveFireStore

class RegisterPresenter (view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: DiveFireStore? = null
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database!!.reference.child("users").child("profile")

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

    fun doSelectProfileImage() {
        showImagePicker(view!!, IMAGE_PROFILE_REQUEST)
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_PROFILE_REQUEST -> {
                if (data != null) {
                    val profilePic = data.getData().toString()

                    val currentUser = auth.currentUser
                    val currentUserDB = databaseReference!!.child((currentUser?.uid!!))
                    currentUserDB.child("profilePic").setValue(profilePic)

                }
            }
        }
    }

}