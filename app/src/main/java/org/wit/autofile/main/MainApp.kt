package org.wit.autofile.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.autofile.models.AutofileStore
import org.wit.autofile.models.firebase.AutofileFireStore

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class MainApp : Application(), AnkoLogger {
  lateinit var autofiles: AutofileStore

  override fun onCreate() {
    super.onCreate()
    //autofilesLocal = AutofileStoreRoom(applicationContext)
    autofiles = AutofileFireStore(applicationContext)
    info("Autofiles app started")
  }
}