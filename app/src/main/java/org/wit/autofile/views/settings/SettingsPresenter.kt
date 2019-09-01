package org.wit.autofile.views.settings


import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BasePresenter
import org.wit.autofile.views.BaseView
import org.wit.autofile.views.VIEW

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

  var autofile = AutofileModel()

  fun doCancel() {
    view?.finish()
  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.autofiles.clear()
    view?.navigateTo(VIEW.LOGIN)
    view?.finish()
  }

  fun countAutofiles() {
    async(UI) {
      view?.displayCount(app.autofiles.findAll().size)
    }

  }
}