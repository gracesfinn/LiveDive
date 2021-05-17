package org.wit.livedive.views.socialMap

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.livedive.BaseView

import org.wit.livedive.databinding.ActivitySocialMapBinding
import org.wit.livedive.models.DiveModel
import org.wit.livedive.views.map.DiveMapPresenter

class SocialMapView: BaseView(), GoogleMap.OnMarkerClickListener  {

    lateinit var presenter: SocialMapPresenter
    lateinit var map : GoogleMap
    private lateinit var binding: ActivitySocialMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySocialMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.init(binding.toolbar, true)

        presenter = initPresenter(SocialMapPresenter(this)) as SocialMapPresenter

        binding.socialMapView.onCreate(savedInstanceState);
        binding.socialMapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadDives()
        }
    }

    override fun showDive(dive: DiveModel?) {
        if (dive != null) {
            binding.socialCurrentTitle.text = dive.title
        }
        if (dive != null) {
            binding.socialCurrentDescription.text = dive.description
        }
        if (dive != null) {
            Glide.with(this).load(dive.image).into(binding.socialCurrentImage);
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
        binding.socialMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.socialMapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        binding.socialMapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.socialMapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.socialMapView.onSaveInstanceState(outState)
    }
}
