package org.wit.autofile.models

interface AutofileStore {
  suspend fun findAll(): List<AutofileModel>
  suspend fun create(autofileModel: AutofileModel)
  suspend fun update(autofile: AutofileModel)
  suspend fun delete(autofile: AutofileModel)
  suspend fun findById(id:Long) : AutofileModel?
  fun clear()
}