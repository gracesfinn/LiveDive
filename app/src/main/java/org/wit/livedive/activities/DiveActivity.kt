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
    val dives = ArrayList<DiveModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive)
        info("Dive Activity started..")

    btnAdd.setOnClickListener()
    {
        dive.title = diveTitle.text.toString()
        dive.description = description.text.toString()
        if (dive.title.isNotEmpty()) {
            dives.add(dive)
            info("add Button Pressed: $dive")
            for (i in dives.indices) {

                info("Dive [$i]:${this.dives[i]}")
            }
        } else {
            toast("Please Enter a title")
        }
    }
}
}