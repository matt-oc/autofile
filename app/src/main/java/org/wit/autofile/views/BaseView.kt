package org.wit.autofile.views

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger

import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.editlocation.EditLocationView
import org.wit.autofile.views.map.AutofileMapView
import org.wit.autofile.views.autofile.AutofileView
import org.wit.autofile.views.autofilelist.AutofileListView
import org.wit.autofile.views.autofilelist.SearchView
import org.wit.autofile.views.login.LoginView
import org.wit.autofile.views.settings.SettingsView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, AUTOFILE, MAPS, LIST, LOGIN, SETTINGS, SEARCH
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null


  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, AutofileListView::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.AUTOFILE -> intent = Intent(this, AutofileView::class.java)
      VIEW.MAPS -> intent = Intent(this, AutofileMapView::class.java)
      VIEW.LIST -> intent = Intent(this, AutofileListView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
      VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
      VIEW.SEARCH -> intent = Intent(this, SearchView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showAutofile(autofile: AutofileModel) {}
  open fun showAutofiles(autofiles: List<AutofileModel>) {}
  open fun displayCount(count: Int) {}
  open fun showProgress() {}
  open fun hideProgress() {}
}