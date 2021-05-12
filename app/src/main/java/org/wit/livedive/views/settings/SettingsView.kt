package org.wit.livedive.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.databinding.ActivitySettingsBinding
import org.wit.livedive.databinding.UserLoginBinding
import org.wit.livedive.models.UserModel


class SettingsView : BaseView()  {

    lateinit var presenter: SettingsPresenter
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        super.initLogin(binding.toolbarAdd)
        setContentView(view)
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("users").child("profile")

        loadProfile()



        binding.userEmail.text = user?.email


        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter


        binding.settingsBtn.setOnClickListener() {
            val email = binding.userEmailUpdated1.text.toString()
            val email2 = binding.userEmailUpdated2.text.toString()

            if (email != email2) {
                toast("Email Mismatch, Please Re-enter")
            }
            else if(email == null){
                toast("Please Enter an Email Address")
            }
            else if(email2 == null){
                toast("Please Verify Email Address")
            }
            else {
                toast("Email Updated")
                presenter.doUpdateEmail(email)
                presenter.doLogout()
            }
        }

        binding.resetPassword.setOnClickListener(){
            toast("Password Reset Sent - Please Check Your Email")
            presenter.doSendPasswordReset()


        }

    }
    private fun loadProfile(){
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.userName.text = snapshot.child("name").value.toString()
                binding.userNumDives.text = snapshot.child("numberOfDives").value.toString()
                binding.userCert.text = snapshot.child("certification").value.toString()
                binding.userCertNum.text = snapshot.child("certNumber").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}