package org.wit.autofile.views.editlocation

import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.autofile.models.Location
import org.wit.autofile.views.BasePresenter
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class EditLocationPresenter(view: BaseView) : BasePresenter(view)  {

  var location = Location()

  init {
    location = view.intent.extras.getParcelable<Location>("location")
  }

  fun doConfigureMap(map: GoogleMap) {
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Autofile")
        .snippet("GPS : " + loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

  fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
    location.lat = lat
    location.lng = lng
    location.zoom = zoom
  }

  fun doSave() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    view?.setResult(0, resultIntent)
    view?.finish()
  }

  fun doUpdateMarker(marker: Marker) {
    val loc = LatLng(location.lat, location.lng)
    marker.setSnippet("GPS : " + loc.toString())
  }
}