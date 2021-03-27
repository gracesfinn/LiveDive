package org.wit.livedive.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dive.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.livedive.R
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.models.DiveModel

class DiveView : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: DivePresenter
    var dive = DiveModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = DivePresenter(this)

        btnAdd.setOnClickListener {
            if (diveTitle.text.toString().isEmpty()) {
                toast(R.string.enter_dive_title)
            } else {
                presenter.doAddOrSave(diveTitle.text.toString(), description.text.toString())
            }
        }

        chooseImage.setOnClickListener { presenter.doSelectImage() }

        diveLocation.setOnClickListener { presenter.doSetLocation() }
    }

    fun showDive(dive: DiveModel) {
        diveTitle.setText(dive.title)
        description.setText(dive.description)
        diveImage.setImageBitmap(readImageFromPath(this, dive.image))
        if (dive.image != null) {
            chooseImage.setText(R.string.change_dive_image)
        }
        btnAdd.setText(R.string.save_dive)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dive, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }
}