package org.wit.livedive.views.dive

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import com.bumptech.glide.Glide
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

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            // set set the transition to be shown when the user enters this activity
            enterTransition = Explode()
            // set the transition to be shown when the user leaves this activity
            exitTransition = Explode()
        }

        binding = ActivityDiveBinding.inflate(layoutInflater)
        val view = binding.root
        super.init(binding.toolbarAdd, true)
        setContentView(view)
        presenter = initPresenter(DivePresenter(this)) as DivePresenter

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        } //Location Selector


        binding.checkBoxWetSuit.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                presenter.doCheckWetsuit(true)
        }

        binding.checkBoxAir.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                presenter.doCheckAir(true)
        }

        binding.checkBoxNitrox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                presenter.doCheckNitrox(true)
        }



        binding.chooseImage.setOnClickListener {
            presenter.cacheDive(
                    binding.diveTitle.text.toString(),
                    binding.description.text.toString(),
                    binding.dateVisited.dayOfMonth,
                    binding.dateVisited.month,
                    binding.dateVisited.year,
                    binding.maxDepth.text.toString(),
                    binding.diveTime.text.toString(),
                    binding.weight.text.toString(),
                    binding.weather.text.toString(),
                    binding.ocean.text.toString(),
                    binding.wildlife.text.toString(),
                    binding.pointOfInterest.text.toString(),
                    binding.additionalNotes.text.toString()
            )
            presenter.doSelectImage()
        } //Image Selector

        binding.chooseImageWildLife.setOnClickListener {
            presenter.cacheDive(
                binding.diveTitle.text.toString(),
                binding.description.text.toString(),
                binding.dateVisited.dayOfMonth,
                binding.dateVisited.month,
                binding.dateVisited.year,
                binding.maxDepth.text.toString(),
                binding.diveTime.text.toString(),
                binding.weight.text.toString(),
                binding.weather.text.toString(),
                binding.ocean.text.toString(),
                binding.wildlife.text.toString(),
                binding.pointOfInterest.text.toString(),
                binding.additionalNotes.text.toString()

            )
            presenter.doSelectImageWildlife()
        } //Wildlife Image Selector

        binding.chooseImagePOI.setOnClickListener {
            presenter.cacheDive(
                binding.diveTitle.text.toString(),
                binding.description.text.toString(),
                binding.dateVisited.dayOfMonth,
                binding.dateVisited.month,
                binding.dateVisited.year,
                binding.maxDepth.text.toString(),
                binding.diveTime.text.toString(),
                binding.weight.text.toString(),
                binding.weather.text.toString(),
                binding.ocean.text.toString(),
                binding.wildlife.text.toString(),
                binding.pointOfInterest.text.toString(),
                binding.additionalNotes.text.toString()

            )
            presenter.doSelectImagePOI()
        } //Wildlife Image Selector

    }

    override fun showLocation (loc: Location){
        binding.lat.setText("%.6f".format(loc.lat))
        binding.lng.setText("%.6f".format(loc.lng))
    }

    override fun showDive(dive: DiveModel?) {
       if (binding.diveTitle.text.isEmpty()) if (dive != null) {
           binding.diveTitle.setText(dive.title)
       }
       if (binding.description.text.isEmpty()) if (dive != null) {
           binding.description.setText(dive.description)
       }
        binding.checkBoxWetSuit.isChecked
        binding.checkBoxAir.isChecked
        binding.checkBoxNitrox.isChecked




       if (dive != null) {
           Glide.with(this).load(dive.image).into(binding.diveImage);
       }
        if (dive != null) {
            Glide.with(this).load(dive.wildlifeImage).into(binding.wildlifeImage);
        }
        if (dive != null) {
            Glide.with(this).load(dive.poiImage).into(binding.POIImage);
        }
       if (dive != null) {
           if (dive.image != null) {
               binding.chooseImage.setText(R.string.change_dive_image)
           }
       }
        if(dive != null){
            if (dive.wildlifeImage != null){
                binding.chooseImageWildLife.setText(R.string.change_wildlife_image)
            }
        }
        if(dive != null){
            if (dive.poiImage != null){
                binding.chooseImagePOI.setText(R.string.change_POI_image)
            }
        }
        if (dive != null) {
            binding.mapView.getMapAsync {
                map = it
                presenter.doConfigureMap(map)
                it.setOnMapClickListener { presenter.doSetLocation() }
            }
            this.showLocation(dive.location)
        }

   }  // Edit Dive




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
                    presenter.doAddOrSave(
                            binding.diveTitle.text.toString(),
                            binding.description.text.toString(),
                            binding.dateVisited.dayOfMonth,
                            binding.dateVisited.month,
                            binding.dateVisited.year,
                            binding.maxDepth.text.toString(),
                            binding.diveTime.text.toString(),
                            binding.weight.text.toString(),
                            binding.weather.text.toString(),
                            binding.ocean.text.toString(),
                            binding.wildlife.text.toString(),
                            binding.pointOfInterest.text.toString(),
                            binding.additionalNotes.text.toString()
                    )
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
    } //When Buttons are pressed

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