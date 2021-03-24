package org.wit.livedive.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Dive Activity started..")

        app = application as MainApp

        var edit = false

        if (intent.hasExtra("dive_edit")) {
            edit = true
            dive = intent.extras?.getParcelable<DiveModel>("dive_edit")!!
            diveTitle.setText(dive.title)
            description.setText(dive.description)
            btnAdd.setText(R.string.save_dive)
        }

    btnAdd.setOnClickListener() {
        dive.title = diveTitle.text.toString()
        dive.description = description.text.toString()
        if (dive.title.isEmpty()) {
            toast(R.string.enter_dive_title)
        } else {
            if (edit) {
                app.dives.update(dive)
            } else {
                app.dives.create(dive)
            }
        }
        info("add Button Pressed: $diveTitle")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dive, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}