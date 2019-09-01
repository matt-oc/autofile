package org.wit.autofile.views.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.autofile.R
import kotlinx.android.synthetic.main.activity_autofile_map.*
import kotlinx.android.synthetic.main.content_autofile_map.*
import org.wit.autofile.helpers.GlideApp
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileMapView : BaseView(), GoogleMap.OnMarkerClickListener {

  lateinit var presenter: AutofileMapPresenter
  lateinit var map : GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_autofile_map)
    super.init(toolbarMaps, true)
    presenter = initPresenter (AutofileMapPresenter(this)) as AutofileMapPresenter

    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      map.setOnMarkerClickListener(this)
      presenter.loadAutofiles()
    }
  }

  override fun showAutofile(autofile: AutofileModel) {
    currentMake.text = autofile.make
    currentModel.text = autofile.model
    GlideApp.with(this).load(autofile.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(imageView);

  }

  override fun showAutofiles(vehicles: List<AutofileModel>) {
    presenter.doPopulateMap(map, vehicles)
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

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}