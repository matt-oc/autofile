package org.wit.autofile.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.autofile.R
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class SettingsView : BaseView(), AnkoLogger {

  var autofile = AutofileModel()
  lateinit var presenter: SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.user_settings)
    super.init(toolbarSettings, true)
    info("Autofile Activity started..")

    presenter = initPresenter (SettingsPresenter(this)) as SettingsPresenter
    presenter.countAutofiles()


    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
      user_email.setText("User: ${user.email}")
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_user_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun displayCount(count: Int) {
    total_autofiles.setText("Total Vehicles is: " + count)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.user_logout -> {
        presenter.doLogout()
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

}