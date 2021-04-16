package org.wit.livedive.models.mem

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.DiveStore

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DiveMemStore : DiveStore, AnkoLogger {

    val dives = ArrayList<DiveModel>()

    override fun findAll(): List<DiveModel> {
        return dives
    }

    override fun create(dive: DiveModel) {
        dive.id = getId()
        dives.add(dive)
        logAll()
    }

    override fun update(dive: DiveModel) {
        var foundDive: DiveModel? = dives.find { p -> p.id == dive.id }
        if (foundDive != null) {
            foundDive.title = dive.title
            foundDive.description = dive.description
            foundDive.image = dive.image
            foundDive.location = dive.location
            logAll();
        }
    }

    override fun delete(dive: DiveModel) {
        dives.remove(dive)
    }

    fun logAll(){
        dives.forEach{ info("${it}")}
    }

    override fun findById(id:Long) : DiveModel? {
        val foundDive: DiveModel? = dives.find { it.id == id }
        return foundDive
    }
}