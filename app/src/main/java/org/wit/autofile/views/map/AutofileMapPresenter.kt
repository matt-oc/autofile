package org.wit.autofile.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BasePresenter
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileMapPresenter(view: BaseView) : BasePresenter(view) {

  fun doPopulateMap(map: GoogleMap, autofiles: List<AutofileModel>) {
    map.uiSettings.setZoomControlsEnabled(true)
    autofiles.forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.make).position(loc)
      map.addMarker(options).tag = it
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
    }
  }

  fun doMarkerSelected(marker: Marker) {
    async(UI) {
      val autofile = marker.tag as AutofileModel
      if (autofile != null) view?.showAutofile(autofile)
    }
  }

  fun loadAutofiles() {
    async(UI) {
      view?.showAutofiles(app.autofiles.findAll())
    }
  }
}