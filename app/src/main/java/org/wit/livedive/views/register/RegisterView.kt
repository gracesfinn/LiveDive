package org.wit.livedive.views.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.databinding.ActivityRegisterBinding
import org.wit.livedive.databinding.UserLoginBinding
import org.wit.livedive.views.login.LoginPresenter

class RegisterView:  BaseView() {

    lateinit var presenter: RegisterPresenter
    private lateinit var binding: ActivityRegisterBinding


    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    private lateinit var userId: String

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





        presenter = initPresenter(RegisterPresenter(this)) as RegisterPresenter


        binding.signUp.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val name = binding.userName.text.toString()
            val certification = binding.certification.text.toString()
            val numDives = binding.NumDives.text.toString()
            val certNum = binding.certNum.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDB = databaseReference!!.child((currentUser?.uid!!))
                        currentUserDB.child("name").setValue(name)
                        currentUserDB.child("numberOfDives").setValue(numDives)
                        currentUserDB.child("certification").setValue(certification)
                        currentUserDB.child("certNumber").setValue(certNum)
                        presenter.doSignUp()
                }
                    else{
                        Toast.makeText(this@RegisterView, "Registration failed, please try again", Toast.LENGTH_LONG).show()
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
}