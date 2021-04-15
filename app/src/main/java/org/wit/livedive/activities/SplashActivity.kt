package org.wit.livedive.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.R
import org.wit.livedive.databinding.ActivitySplashBinding
import org.wit.livedive.views.divelist.DiveListView


class SplashActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_splash)

        //4second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashActivity, DiveListView::class.java))
            info("Splash Activity started..")
            //finish this activity
            finish()
        }, 4000)
    }
}