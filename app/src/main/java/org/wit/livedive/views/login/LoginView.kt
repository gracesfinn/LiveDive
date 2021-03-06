package org.wit.livedive.views.login

import android.os.Bundle
import android.view.View
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.databinding.UserLoginBinding


class LoginView : BaseView() {

    lateinit var presenter: LoginPresenter
    private lateinit var binding: UserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserLoginBinding.inflate(layoutInflater)
        val view = binding.root
        super.initLogin(binding.toolbar)
        setContentView(view)
        binding.progressBar.visibility = View.GONE


        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        binding.signUp.setOnClickListener {

                presenter.doSignUp()

        }

        binding.logIn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                presenter.doLogin(email,password)
            }
        }
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}