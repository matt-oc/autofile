package org.wit.autofile.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.autofile.helpers.readImageFromPath
import org.wit.autofile.models.AutofileModel
import org.wit.autofile.models.AutofileStore
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Matthew O'Connor
 * 2019
 * Applied Computing
 */

class AutofileFireStore(val context: Context) : AutofileStore, AnkoLogger {

  val autofiles = ArrayList<AutofileModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference
  lateinit var st: StorageReference

  suspend override fun findAll(): List<AutofileModel> {
    return autofiles
  }

  suspend override fun findById(id: Long): AutofileModel? {
    val foundAutofile: AutofileModel? = autofiles.find { p -> p.id == id }
    return foundAutofile
  }

  suspend override fun create(autofile: AutofileModel) {
    val key = db.child("users").child(userId).child("autofiles").push().key
    key?.let {
      autofile.fbId = key
      autofiles.add(autofile)
      db.child("users").child(userId).child("autofiles").child(key).setValue(autofile)
      updateImage(autofile)
    }
  }

  suspend override fun update(autofile: AutofileModel) {
    var foundAutofile: AutofileModel? = autofiles.find { p -> p.fbId == autofile.fbId }
    if (foundAutofile != null) {
      foundAutofile.make = autofile.make
      foundAutofile.model = autofile.model
      foundAutofile.color = autofile.color
      foundAutofile.date = autofile.date
      foundAutofile.favourite = autofile.favourite
      foundAutofile.rating = autofile.rating
      foundAutofile.image = autofile.image
      foundAutofile.location = autofile.location
    }

    db.child("users").child(userId).child("autofiles").child(autofile.fbId).setValue(autofile)
    if ((autofile.image.length) > 0 && (autofile.image[0] != 'h')) {
      updateImage(autofile)
    }
  }

  suspend override fun delete(autofile: AutofileModel) {
    db.child("users").child(userId).child("autofiles").child(autofile.fbId).removeValue()
    autofiles.remove(autofile)
  }

  override fun clear() {
    autofiles.clear()
  }

  fun updateImage(autofile: AutofileModel) {
    if (autofile.image != "") {
      val fileName = File(autofile.image)
      val imageName = fileName.getName()

      var imageRef = st.child(userId + '/' + imageName)
      val baos = ByteArrayOutputStream()
      val bitmap = readImageFromPath(context, autofile.image)

      bitmap?.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
          println(it.message)
        }.addOnSuccessListener { taskSnapshot ->
          taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
            autofile.image = it.toString()
            db.child("users").child(userId).child("autofiles").child(autofile.fbId).setValue(autofile)
          }
        }
      }
    }
  }

  fun fetchAutofiles(autofilesReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(dataSnapshot: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot!!.children.mapNotNullTo(autofiles) { it.getValue<AutofileModel>(AutofileModel::class.java) }
        autofilesReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    st = FirebaseStorage.getInstance().reference
    autofiles.clear()
    db.child("users").child(userId).child("autofiles").addListenerForSingleValueEvent(valueEventListener)
  }
}