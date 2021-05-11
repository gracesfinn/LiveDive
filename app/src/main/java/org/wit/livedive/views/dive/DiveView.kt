package org.wit.livedive.views.dive

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.Explode
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.RatingBar
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.databinding.ActivityDiveBinding
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

        binding.favCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toast("Added to Favourites")
                presenter.doCheckFavourite(true)
        }

        binding.ratingBar.setOnRatingBarChangeListener(object :
            RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratignBar: RatingBar?, rating: Float, fromUser: Boolean) {
                toast("Rating is: $rating")
                presenter.doCheckRatingBar(rating)
            }

        })



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

        binding.detailsBtn.setOnClickListener{
            if(binding.detailsCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.detailsCard.visibility = View.VISIBLE
                binding.detailsBtn.text = "Close Details"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.detailsCard.visibility = View.GONE
                binding.detailsBtn.text = "Dive Details"
            }
        }

        binding.siteLocationBtn.setOnClickListener{
            if(binding.mapCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.mapCard.visibility = View.VISIBLE
                binding.siteLocationBtn.text = "Close Map"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.mapCard.visibility = View.GONE
                binding.siteLocationBtn.text = "Dive Location"
            }
        }

        binding.dateBtn.setOnClickListener{
            if(binding.dateCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.dateCard.visibility = View.VISIBLE
                binding.dateBtn.text = "Close Date Picker"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.dateCard.visibility = View.GONE
                binding.dateBtn.text = "Date of Dive"
            }
        }

        binding.equipmentBtn.setOnClickListener{
            if(binding.equipmentCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.equipmentCard.visibility = View.VISIBLE
                binding.equipmentBtn.text = "Close Equipment"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.equipmentCard.visibility = View.GONE
                binding.equipmentBtn.text = "Dive Equipment"
            }
        }

        binding.conditonsBtn.setOnClickListener{
            if(binding.conditionsCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.conditionsCard.visibility = View.VISIBLE
                binding.conditonsBtn.text = "Close Conditions"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.conditionsCard.visibility = View.GONE
                binding.conditonsBtn.text = "Dive Conditions"
            }
        }

        binding.wildlifeBtn.setOnClickListener{
            if(binding.wildlifeCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.wildlifeCard.visibility = View.VISIBLE
                binding.wildlifeBtn.text = "Close Marine Life"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.wildlifeCard.visibility = View.GONE
                binding.wildlifeBtn.text = "Marine Life"
            }
        }

        binding.poiBtn.setOnClickListener{
            if(binding.poiCard.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.poiCard.visibility = View.VISIBLE
                binding.poiBtn.text = "Close Points of Interest"
            }
            else{
                TransitionManager.beginDelayedTransition(binding.cardView,AutoTransition())
                binding.poiCard.visibility = View.GONE
                binding.poiBtn.text = "Points of Interest"
            }
        }



    }

    override fun showLocation (loc: Location){
        binding.lat.setText("%.6f".format(loc.lat))
        binding.lng.setText("%.6f".format(loc.lng))
    }

      override fun showDive(dive:DiveModel?) {

          if (binding.diveTitle.text.isEmpty()) if (dive != null) {
           binding.diveTitle.setText(dive.title)
          }
          if (binding.description.text.isEmpty()) if (dive != null) {
           binding.description.setText(dive.description)
          }
          if (binding.maxDepth.text.isEmpty()) if (dive != null) {
              binding.maxDepth.setText(dive.maxDepth)
          }
          if (binding.diveTime.text.isEmpty()) if (dive != null) {
              binding.diveTime.setText(dive.mins)
          }
          if (binding.weight.text.isEmpty()) if (dive != null) {
              binding.weight.setText(dive.weight)
          }
          if (binding.weather.text.isEmpty()) if (dive != null) {
              binding.weather.setText(dive.weather)
          }
          if (binding.ocean.text.isEmpty()) if (dive != null) {
              binding.ocean.setText(dive.ocean)
          }
          if (binding.wildlife.text.isEmpty()) if (dive != null) {
              binding.wildlife.setText(dive.wildlife)
          }
          if (binding.pointOfInterest.text.isEmpty()) if (dive != null) {
              binding.pointOfInterest.setText(dive.poi)
          }
          binding.checkBoxWetSuit.isChecked
          binding.checkBoxAir.isChecked
          binding.checkBoxNitrox.isChecked
          binding.favCheckBox.isChecked

          if (dive != null) {
              binding.dateVisited.updateDate(dive.yearVisited, dive.monthVisited, dive.dayVisited)
          }


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