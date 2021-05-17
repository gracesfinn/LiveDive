package org.wit.livedive.views.register

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.IMAGE_PROFILE_REQUEST
import org.wit.livedive.databinding.ActivityRegisterBinding
import org.wit.livedive.databinding.UserLoginBinding
import org.wit.livedive.views.login.LoginPresenter
import java.io.ByteArrayOutputStream

class RegisterView:  BaseView() {

    lateinit var presenter: RegisterPresenter
    private lateinit var binding: ActivityRegisterBinding
    private val REQUEST_IMAGE_CAPTURE = 100


   // var databaseReference: DatabaseReference? = null
    //var database: FirebaseDatabase? = null
  //  lateinit var auth: FirebaseAuth
  //  private lateinit var userId: String
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        super.initLogin(binding.toolbarAdd)
        setContentView(view)
        //binding.progressBar.visibility = View.GONE

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("users").child("profile")

        binding.profileImageBtn.setOnClickListener {
            /*presenter.cacheDive(
                    binding.diveTitle.text.toString(),
                    binding.description.text.toString(),
                    binding.dateVisited.dayOfMonth,
                    binding.dateVisited.month,
                    binding.dateVisited.year,
                    binding.maxDepth.text.toString(),
                    binding.diveTime.text.toString(),
                    binding.weight.text.toString(),
                    binding.weather.text.toString(),
                    binding.ocean.text.toString(),
                    binding.wildlife.text.toString(),
                    binding.pointOfInterest.text.toString(),
                    binding.additionalNotes.text.toString()
            ) */
            //presenter.doSelectProfileImage()
            takePictureIntent()
        }





        presenter = initPresenter(RegisterPresenter(this)) as RegisterPresenter


        binding.signUp.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val password2 = binding.password2.text.toString()
            val name = binding.userName.text.toString()
            val certification = binding.certification.text.toString()
            val numDives = binding.NumDives.text.toString()
            val certNum = binding.certNum.text.toString()
            val diveStatus = binding.diveStatus.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else if (password != password2)
            {
                toast("Passwords do not match, please re-enter")
            }

                else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUserDB = databaseReference!!.child((currentUser?.uid!!))
                        currentUserDB.child("name").setValue(name)
                        currentUserDB.child("numberOfDives").setValue(numDives)
                        currentUserDB.child("certification").setValue(certification)
                        currentUserDB.child("certNumber").setValue(certNum)
                        currentUserDB.child("diveStatus").setValue(diveStatus)
                        presenter.doSignUp()
                    } else {
                        Toast.makeText(
                            this@RegisterView,
                            "Registration failed, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }

            }
        }


    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { pictureIntent ->
            pictureIntent.resolveActivity(packageManager!!).also {
                startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult(requestCode, resultCode, data)
        val REQUEST_IMAGE_CAPTURE = 100
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val storageReference =
            FirebaseStorage.getInstance().reference.child("users").child("profile")

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val profileImage = baos.toByteArray()

        val upload = storageReference.putBytes(profileImage)

        upload.addOnCompleteListener { uploadTask ->
            if (upload.isSuccessful) {
                storageReference.downloadUrl.addOnCompleteListener { urlTask ->
                    urlTask.result?.let {
                        imageUri = it
                        toast(imageUri.toString())

                        binding.profileImage.setImageBitmap(bitmap)
                    }
                }
            } else {
                uploadTask.exception?.let {

                    toast(it.message!!)

                }
            }
        }
    }
}





/*override fun showProgress() {
    binding.progressBar.visibility = View.VISIBLE
}

override fun hideProgress() {
    binding.progressBar.visibility = View.GONE
} */
