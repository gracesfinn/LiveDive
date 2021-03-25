package org.wit.livedive.activities

import android.content.Intent
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
import org.wit.livedive.helpers.readImage
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.helpers.showImagePicker
import org.wit.livedive.main.MainApp


class DiveActivity : AppCompatActivity() , AnkoLogger {

    var dive = DiveModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive)
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
            diveImage.setImageBitmap(readImageFromPath(this, dive.image))
            if (dive.image != null) {
                chooseImage.setText(R.string.change_dive_image)
            }
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

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    dive.image = data.getData().toString()
                    diveImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_dive_image)
                }
            }
        }
    }
}