package org.wit.autofile.views.autofilelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_autofile.view.*
import kotlinx.android.synthetic.main.card_autofile.view.*
import kotlinx.android.synthetic.main.card_autofile.view.autofileMake
import kotlinx.android.synthetic.main.card_autofile.view.rating
import org.wit.autofile.R
import org.wit.autofile.helpers.GlideApp
import org.wit.autofile.models.AutofileModel

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

interface AutofileListener {
  fun onAutofileClick(autofile: AutofileModel)
}

class AutofileAdapter constructor(private var autofiles: List<AutofileModel>,
                                   private val listener: AutofileListener) : RecyclerView.Adapter<AutofileAdapter.MainHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_autofile, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val autofile = autofiles[holder.adapterPosition]
    holder.bind(autofile, listener)
  }


  override fun getItemCount(): Int = autofiles.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(autofile: AutofileModel, listener: AutofileListener) {
      itemView.autofileMake.text = autofile.make
      itemView.autofileModel.text = autofile.model
      itemView.rating.rating = autofile.rating
      itemView.autofilefavourite.isChecked = autofile.favourite
      GlideApp.with(itemView.context).load(autofile.image).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(itemView.imageIcon);
      itemView.setOnClickListener { listener.onAutofileClick(autofile) }
    }
  }
}