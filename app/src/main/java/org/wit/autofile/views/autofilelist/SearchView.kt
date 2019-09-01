package org.wit.autofile.views.autofilelist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_autofile_list.*
import org.wit.autofile.R
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.views.BaseView

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class SearchView : BaseView(), AutofileListener {

  lateinit var presenter: AutofileListPresenter
  var fav = false
  var search = false
  lateinit var searchString: String

  private lateinit var searchView: SearchView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_autofile_list)
    super.init(toolbarMain, false)

    presenter = initPresenter(AutofileListPresenter(this)) as AutofileListPresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
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
      }
    } else if (search == true) {
      for (file in autofiles) {
        if (file.make == searchString.toUpperCase() || file.model == searchString.toUpperCase() || file.color == searchString.toUpperCase()) {
          list.add(file)
        }
        recyclerView.adapter = AutofileAdapter(list, this)
        recyclerView.adapter?.notifyDataSetChanged()
      }
    } else
      recyclerView.adapter = AutofileAdapter(autofiles, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_search, menu)
    val searchItem = menu!!.findItem(R.id.searchView)
    searchView = searchItem.actionView as SearchView
    searchView.setSubmitButtonEnabled(true)
    searchView.setQueryHint("Search...")
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextChange(newText: String): Boolean {
        return false
      }
      override fun onQueryTextSubmit(query: String): Boolean {
        search = true
        searchString = query
        presenter.loadAutofiles()
        return true
      }
    })

    return super.onCreateOptionsMenu(menu)
  }


  override fun onAutofileClick(autofile: AutofileModel) {
    presenter.doEditAutofile(autofile)
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadAutofiles()
    super.onActivityResult(requestCode, resultCode, data)
  }


}