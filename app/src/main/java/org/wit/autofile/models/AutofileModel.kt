package org.wit.autofile.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Embedded
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class AutofileModel(@PrimaryKey(autoGenerate = true)var id: Long = 0,
                          var make: String = "",
                          var model: String = "",
                          var favourite: Boolean = false,
                          var rating: Float = 1.0f,
                          var color: String = "",
                          var image: String = "",
                          var date: String = "",
                          var fbId : String = "",
                         @Embedded var location : Location = Location()): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

