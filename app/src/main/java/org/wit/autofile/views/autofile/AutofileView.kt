package org.wit.autofile.views.autofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_autofile.*
import kotlinx.android.synthetic.main.activity_autofile.autofileMake
import kotlinx.android.synthetic.main.activity_autofile.rating
import kotlinx.android.synthetic.main.card_autofile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.autofile.R
import org.wit.autofile.helpers.GlideApp
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileView : BaseView(), AnkoLogger {

  lateinit var presenter: AutofilePresenter
  lateinit var map: GoogleMap
  var autofile = AutofileModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_autofile)
    super.init(toolbar, true)
    info("Autofile Activity started..")

    presenter = initPresenter (AutofilePresenter(this)) as AutofilePresenter

    chooseImage.setOnClickListener { presenter.doSelectImage()}

    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      presenter.doConfigureMap(map)
      it.setOnMapClickListener { presenter.doSetLocation() }
    }
  }

  override fun showAutofile(autofile: AutofileModel) {
    autofileMake.setText(autofile.make)
    model.setText(autofile.model)
    color.setText(autofile.color)
    favourite.setChecked(autofile.favourite)
    rating.setRating(autofile.rating)
    dateViewed.setText(autofile.date)
    GlideApp.with(this).load(autofile.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(autofileImage);
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_autofile_edit, menu)
    if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }

      R.id.item_save -> {
        autofile.make = autofileMake.text.toString().toUpperCase()
        autofile.model = model.text.toString().toUpperCase()
        autofile.color = color.text.toString().toUpperCase()
        autofile.favourite = favourite.isChecked()
        autofile.rating = rating.getRating()
        autofile.date = dateViewed.text.toString()
          if (autofile.make.isEmpty()) {
            toast(R.string.enter_autofile_make)
          }
        else {
            presenter.doAddOrSave(autofile.make, autofile.model, autofile.color, autofile.date, autofile.rating, autofile.favourite)
          }
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    Log.i("REQCODE", requestCode.toString() + resultCode.toString())
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
    else if (requestCode == 3) {
      val intent = Intent()
      presenter.doActivityResult(requestCode, resultCode, intent)
    }
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
    presenter.doResartLocationUpdates()
  }

  override fun onBackPressed() {
    presenter.doCancel()
  }
}