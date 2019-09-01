package org.wit.autofile.views.autofile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.autofile.R
import org.wit.autofile.main.MainApp
import org.wit.autofile.models.Location
import org.wit.autofile.models.AutofileModel
import org.jetbrains.anko.toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.wit.autofile.helpers.*
import org.wit.autofile.views.*
import java.io.File

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofilePresenter(view: BaseView) : BasePresenter(view) {

  val locationRequest = createDefaultLocationRequest()

  var autofile = AutofileModel()
  var defLocation = Location(52.245696, -7.139102, 15f)
  var edit = false;

  var map: GoogleMap? = null
  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("autofile_edit")) {
      edit = true
      autofile = view.intent.extras.getParcelable<AutofileModel>("autofile_edit")
      view.showAutofile(autofile)
      checkImagePermissions(view)
    }
    else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
    }
  }

  fun doAddOrSave(make: String, model: String, color: String, dateVisited: String, rating: Float, favourite: Boolean) {
    autofile.make = make
    autofile.model = model
    autofile.color = color
    autofile.date = dateVisited
    autofile.rating = rating
    autofile.favourite = favourite
    async(UI) {
      if (edit) {
        app.autofiles.update(autofile)
      } else {
        app.autofiles.create(autofile)
      }
      view?.finish()
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    async(UI) {
      app.autofiles.delete(autofile)
      view?.toast(R.string.autofile_delete)
      view?.finish()
    }
  }


  fun doSelectImage() {
    view?.let {
      showImagePicker(view!!, IMAGE_REQUEST)
    }
  }

  fun doSetLocation() {
      view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(autofile.location.lat, autofile.location.lng, autofile.location.zoom))

  }

  override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
        doSetCurrentLocation()
    } else {
      // permissions denied, so use the default location
      locationUpdate(defLocation)
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          locationUpdate(Location(l.latitude, l.longitude))
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }

    fun doConfigureMap(m: GoogleMap) {
      map = m
      locationUpdate(autofile.location)
    }

    fun locationUpdate(location: Location) {
      autofile.location = location
      autofile.location.zoom = 15f
      map?.clear()
      map?.uiSettings?.setZoomControlsEnabled(true)
      val options = MarkerOptions().title(autofile.make).position(LatLng(autofile.location.lat, autofile.location.lng))
      map?.addMarker(options)
      map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(autofile.location.lat, autofile.location.lng), autofile.location.zoom))
      view?.showAutofile(autofile)
    }

  @SuppressLint("MissingPermission")
  fun doResartLocationUpdates() {
    var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          locationUpdate(Location(l.latitude, l.longitude))
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
      Log.i("REQUEST CODE: ", requestCode.toString())
      when (requestCode) {
        GALLERY -> {
          autofile.image = data.data.toString()
          view?.showAutofile(autofile)
        }
        LOCATION_REQUEST -> {
          val location = data.extras.getParcelable<Location>("location")
            autofile.location = location
            locationUpdate(location)
        }
        CAMERA -> {
          if (mCurrentPhotoPath != "") {
            if (resultCode == Activity.RESULT_OK) {
              autofile.image = cameraPicSaveAndGet(mCurrentPhotoPath)
              Log.i("Image URI", autofile.image);
              view?.showAutofile(autofile)
            }

          }
        }
      }
    }

  fun cameraPicSaveAndGet(path : String): String {
    val f = File(path)
    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
      mediaScanIntent.data = Uri.fromFile(f)
      app.sendBroadcast(mediaScanIntent)
    }
    return Uri.fromFile(f).toString()
  }
  }
