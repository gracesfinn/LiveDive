package org.wit.livedive.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dive.*
import org.jetbrains.anko.toast
import org.wit.livedive.R
import org.wit.livedive.models.DiveModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class DiveActivity : AppCompatActivity() , AnkoLogger {

    var dive = DiveModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive)
        info("Dive Activity started..")

    btnAdd.setOnClickListener()
    {
        dive.title = diveTitle.text.toString()
        if (dive.title.isNotEmpty()) {
            info("add Button Pressed: $dive")
        } else {
            toast("Please Enter a title")
        }
    }
}
}