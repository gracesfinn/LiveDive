package org.wit.livedive.views.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

import org.wit.livedive.BaseView
import org.wit.livedive.databinding.ActivityDiveMapsBinding
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.models.DiveModel

class DiveMapView : BaseView(), GoogleMap.OnMarkerClickListener  {

    lateinit var presenter: DiveMapPresenter
    lateinit var map : GoogleMap
    private lateinit var binding: ActivityDiveMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDiveMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.init(binding.toolbar, true)

        presenter = initPresenter(DiveMapPresenter(this)) as DiveMapPresenter

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadDives()
        }
    }

    override fun showDive(dive: DiveModel?) {
        if (dive != null) {
            binding.currentTitle.text = dive.title
        }
        if (dive != null) {
            binding.currentDescription.text = dive.description
        }
        if (dive != null) {
            binding.currentImage.setImageBitmap(dive.image?.let { readImageFromPath(this, it) })
        }
    }

    override fun showDives(dives: List<DiveModel>) {
        presenter.doPopulateMap(map, dives)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}