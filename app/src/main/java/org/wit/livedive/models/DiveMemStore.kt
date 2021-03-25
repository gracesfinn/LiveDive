package org.wit.livedive.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

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
            foundDive.lat = dive.lat
            foundDive.lng = dive.lng
            foundDive.zoom = dive.zoom
            logAll()
        }
    }

    fun logAll(){
        dives.forEach{ info("${it}")}
    }
}