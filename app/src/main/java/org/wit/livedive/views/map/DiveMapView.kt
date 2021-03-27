package org.wit.livedive.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_dive_maps.*
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.models.DiveModel

class DiveMapView : BaseView(), GoogleMap.OnMarkerClickListener  {

    lateinit var presenter: DiveMapPresenter
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dive_maps)
        super.init(toolbar)

        presenter = initPresenter(DiveMapPresenter(this)) as DiveMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadDives()
        }
    }

    override fun showDive(dive: DiveModel) {
        currentTitle.text = dive.title
        currentDescription.text = dive.description
        currentImage.setImageBitmap(readImageFromPath(this, dive.image))
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
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}