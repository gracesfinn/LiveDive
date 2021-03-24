package org.wit.livedive.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dive.*
import org.jetbrains.anko.toast
import org.wit.livedive.R
import org.wit.livedive.models.DiveModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.main.MainApp


class DiveActivity : AppCompatActivity() , AnkoLogger {

    var dive = DiveModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive)
        app = application as MainApp
        info("Dive Activity started..")

    btnAdd.setOnClickListener()
    {
        dive.title = diveTitle.text.toString()
        dive.description = description.text.toString()
        if (dive.title.isNotEmpty()) {
            app!!.dives.add(dive)
            info("add Button Pressed: ${dive}")
            for (i in app!!.dives.indices) {

                info("Dive [$i]:${app!!.dives[i]}")
            }
        } else {
            toast("Please Enter a title")
        }
    }
}
}