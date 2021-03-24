package org.wit.livedive.models

interface DiveStore {
    fun findAll(): List<DiveModel>
    fun create(dive: DiveModel)
    fun update(dive: DiveModel)
}