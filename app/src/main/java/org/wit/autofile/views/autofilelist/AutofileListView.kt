package org.wit.autofile.views.autofilelist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_autofile_list.*
import org.jetbrains.anko.toast
import org.wit.autofile.R
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileListView : BaseView(), AutofileListener {

  lateinit var presenter: AutofileListPresenter
  var fav = false

  private lateinit var searchView: SearchView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_autofile_list)
    super.init(toolbarMain, false)

    presenter = initPresenter(AutofileListPresenter(this)) as AutofileListPresenter

    item_add.setOnClickListener {  presenter.doAddAutofile()}
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadAutofiles()


  }

   override fun showAutofiles(autofiles: List<AutofileModel>) {
     val list = mutableListOf<AutofileModel>()
     if (fav == true) {

       for (file in autofiles) {
         if (file.favourite == true) {
           list.add(file)
         }
         recyclerView.adapter = AutofileAdapter(list, this)
         recyclerView.adapter?.notifyDataSetChanged()
         toast(R.string.favourites_view)
       }
     } else
    recyclerView.adapter = AutofileAdapter(autofiles, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.user_logout -> {
        presenter.doLogout()
        finish()
      }

      R.id.user_settings -> {
        presenter.doSettings()
      }

      R.id.item_map -> {
        presenter.doShowAutofilesMap()
      }

      R.id.item_search -> {
        presenter.doSearch()
      }

      R.id.toggle_favourites -> {
        if (fav == true) {
            fav = false
          presenter.loadAutofiles()
        }
        else {
          fav = true
          presenter.loadAutofiles()
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }


  override fun onAutofileClick(autofile: AutofileModel) {
    presenter.doEditAutofile(autofile)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadAutofiles()
    super.onActivityResult(requestCode, resultCode, data)
  }


}