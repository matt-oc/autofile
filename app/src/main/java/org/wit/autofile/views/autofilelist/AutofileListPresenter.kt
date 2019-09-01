package org.wit.autofile.views.autofilelist

import com.google.firebase.auth.FirebaseAuth
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BasePresenter
import org.wit.autofile.views.BaseView
import org.wit.autofile.views.VIEW
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddAutofile() {
    view?.navigateTo(VIEW.AUTOFILE)
  }

  fun doEditAutofile(autofile: AutofileModel) {
    view?.navigateTo(VIEW.AUTOFILE, 0, "autofile_edit", autofile)
  }

  fun doShowAutofilesMap() {
    view?.navigateTo(VIEW.MAPS)
  }

  fun doLogout() {
    FirebaseAuth.getInstance().signOut()
    app.autofiles.clear()
    view?.navigateTo(VIEW.LOGIN)
    view?.finish()
  }

  fun doSettings() {
    view?.navigateTo(VIEW.SETTINGS)
  }

  fun doSearch() {
    view?.navigateTo(VIEW.SEARCH)
  }

  fun loadAutofiles() {
    async(UI) {
      view?.showAutofiles(app.autofiles.findAll())
    }
    }
  }

