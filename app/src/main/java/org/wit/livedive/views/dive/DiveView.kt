package org.wit.livedive.views.dive

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.databinding.ActivityDiveBinding
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location

class DiveView : BaseView(), AnkoLogger {

    lateinit var presenter: DivePresenter
    var dive = DiveModel()
    lateinit var map: GoogleMap
    private lateinit var binding: ActivityDiveBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiveBinding.inflate(layoutInflater)
        val view = binding.root
        super.init(binding.toolbarAdd, true)
        setContentView(view)

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        presenter = initPresenter(DivePresenter(this)) as DivePresenter

        binding.chooseImage.setOnClickListener {
            presenter.cacheDive(binding.diveTitle.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }

    }

   override fun showDive(dive: DiveModel?) {
       if (binding.diveTitle.text.isEmpty()) if (dive != null) {
           binding.diveTitle.setText(dive.title)
       }
       if (binding.description.text.isEmpty()) if (dive != null) {
           binding.description.setText(dive.description)
       }
       if (dive != null) {
           binding.diveImage.setImageBitmap(dive.image?.let { readImageFromPath(this, it) })
       }
       if (dive != null) {
           if (dive.image != null) {
               binding.chooseImage.setText(R.string.change_dive_image)
           }
           this.showLocation(dive.location)
       }
   }
       override fun showLocation (loc: Location){
           binding.lat.setText("%.6f".format(dive.location.lat))
           binding.lng.setText("%.6f".format(dive.location.lng))
       }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dive, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (binding.diveTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_dive_title)
                } else {
                    presenter.doAddOrSave(binding.diveTitle.text.toString(), binding.description.text.toString())
                }
            }
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
    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }


}