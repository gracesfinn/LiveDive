package org.wit.livedive.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.databinding.ActivitySettingsBinding
import org.wit.livedive.databinding.UserLoginBinding


class SettingsView : BaseView()  {

    lateinit var presenter: SettingsPresenter
    override var user = FirebaseAuth.getInstance().currentUser
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        super.initLogin(binding.toolbarAdd)
        setContentView(view)
        super.onCreate(savedInstanceState)

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